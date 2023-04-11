package com.eugene.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.cache.ICouponActivityCacheService;
import com.eugene.cache.ICouponTemplateCacheService;
import com.eugene.common.enums.StatusEnum;
import com.eugene.common.exception.BusinessException;
import com.eugene.controller.request.AddCouponActivityRequest;
import com.eugene.controller.request.CouponActivityRequest;
import com.eugene.controller.request.ReceiveCouponRequest;
import com.eugene.controller.request.UserCouponRequest;
import com.eugene.controller.response.CouponActivityResponse;
import com.eugene.controller.response.CouponResponse;
import com.eugene.mapper.CouponActivityLogMapper;
import com.eugene.mapper.CouponActivityMapper;
import com.eugene.mapper.CouponMapper;
import com.eugene.pojo.Coupon;
import com.eugene.pojo.CouponActivity;
import com.eugene.pojo.CouponActivityLog;
import com.eugene.pojo.CouponTemplate;
import com.eugene.service.ICouponActivityService;
import com.eugene.service.ICouponCacheService;
import com.eugene.utils.CouponRedisLuaUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.eugene.common.constant.RedisKeyConstant.getReceiveCouponKey;
import static com.eugene.common.enums.Errors.NOT_JOIN_RECEIVE_COUPON_ERROR;
import static com.eugene.common.enums.Errors.RECEIVE_COUPON_ERROR;
import static com.eugene.controller.response.CouponActivityResponse.buildCouponActivityResponse;
import static com.eugene.controller.response.CouponResponse.buildCouponResponse;
import static com.eugene.pojo.Coupon.buildCoupon;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/7 18:30
 */
@Service
public class CouponActivityServiceImpl implements ICouponActivityService {

    private static final Logger log = LoggerFactory.getLogger(CouponActivityServiceImpl.class);

    @Autowired
    private CouponActivityMapper couponActivityMapper;
    @Autowired
    private ICouponActivityCacheService couponActivityCacheService;
    @Autowired
    private CouponActivityLogMapper couponActivityLogMapper;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private CouponRedisLuaUtil couponRedisLuaUtil;
    @Autowired
    private ICouponCacheService couponCacheService;
    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private ICouponTemplateCacheService couponTemplateCacheService;

    @Override
    public boolean addCouponActivity(AddCouponActivityRequest request) {
        CouponActivity couponActivity = new CouponActivity();
        couponActivity.setName(request.getName());
        couponActivity.setCouponTemplateCode(request.getCouponTemplateCode());
        couponActivity.setTotalNumber(request.getTotalNumber());
        couponActivity.setLimitNumber(request.getLimitNumber());
        couponActivity.setStatus(request.getStatus());
        couponActivity.setBeginTime(request.getBeginTime());
        couponActivity.setEndTime(request.getEndTime());
        couponActivity.setCreateTime(new Date());
        couponActivity.setUpdateTime(new Date());
        int result = couponActivityMapper.insert(couponActivity);
        if (result > 0) {
            // 保存到Redis缓存中
            couponActivityCacheService.setCouponActivityCache(couponActivity);
        }
        return result > 0;
    }

    @Override
    public List<CouponActivityResponse> getCouponCenterList(UserCouponRequest request) {
        // 查询数据库, todo 优化改为查询Redis
        QueryWrapper<CouponActivity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("status", StatusEnum.AVAILABLE.getCode());
        List<CouponActivity> couponActivities = couponActivityMapper.selectList(queryWrapper);
        return couponActivities.stream()
                // todo receivedNumber 取真实领取数量 改为查询Redis
        //        QueryWrapper<CouponActivityLog> queryWrapper = new QueryWrapper<>();
        //        queryWrapper.eq("coupon_activity_id", request.getCouponActivityId());
        //        Integer receivedNumber = couponActivityLogMapper.selectCount(queryWrapper);
                .map(couponActivity -> buildCouponActivityResponse(couponActivity, 0L))
                .collect(Collectors.toList());
    }

    @Override
    public CouponActivityResponse getCouponActivityDetail(CouponActivityRequest request) {
        CouponActivity couponActivityCache = couponActivityCacheService.getCouponActivityCache(request.getCouponActivityId());
        // 查询当前用户已领取数量, todo 优化改为查询Redis
        QueryWrapper<CouponActivityLog> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coupon_activity_id", request.getCouponActivityId());
        Integer receivedNumber = couponActivityLogMapper.selectCount(queryWrapper);
        return buildCouponActivityResponse(couponActivityCache, Long.valueOf(receivedNumber));
    }

    /**
     * 领取优惠券
     * 1、请求量大、并发写
     * 2、优惠券要控制不能超发、每人不能超领，【防刷】
     *
     * @param request
     * @return
     */
    @Override
    public CouponResponse receive(ReceiveCouponRequest request) throws BusinessException {
        CouponResponse couponResponse = null;
        CouponActivity couponActivityCache = couponActivityCacheService.getCouponActivityCache(request.getCouponActivityID());
        // 检查是否可参与领券活动
        boolean canJoin = checkIsCanJoinActivity(couponActivityCache);
        if (!canJoin) {
            throw new BusinessException(NOT_JOIN_RECEIVE_COUPON_ERROR.getCode(), NOT_JOIN_RECEIVE_COUPON_ERROR.getMsg());
        }
        // 参与活动，加锁防重复提交
        RLock lock = redissonClient.getLock(getReceiveCouponKey(request.getUserId(), request.getCouponActivityID()));
        if (lock.tryLock()) {
            try {
                // 领取优惠券，received 用户是否已领取成功
                boolean received = true;
                if (couponActivityCache.existLimit()) {
                    received = couponRedisLuaUtil.receive(request);
                }
                // 添加用户优惠券信息， todo优化：原子性
                if (received) {
                    CouponTemplate couponTemplateCache = couponTemplateCacheService.getCouponTemplateCache(couponActivityCache.getCouponTemplateCode());
                    Coupon coupon = buildCoupon(request, couponTemplateCache);
                    // 保存优惠券信息，
                    couponCacheService.setCouponCache(coupon);
                    couponCacheService.addUserCouponCode(request.getMobile(), coupon.getCode());
                    // todo 优化：改为MQ异步更新Mysql数据库，提高性能
                    // todo 遇到了新问题，MQ如何保障消息不丢失，如何保障Redis和Mysql之间的数据一致性
                    couponMapper.insert(coupon);
                    // 保存领券活动参与记录 todo 改为MQ异步更新Mysql数据库，提高性能
                    CouponActivityLog couponActivityLog = getCouponActivityLog(request, coupon);
                    couponActivityLogMapper.insert(couponActivityLog);
                    // 组装返回领取的优惠券信息
                    couponResponse = buildCouponResponse(coupon);
                } else {
                    throw new BusinessException(RECEIVE_COUPON_ERROR.getCode(), RECEIVE_COUPON_ERROR.getMsg());
                }
            } finally {
                if (lock.isLocked()) {
                    // TODO 重点注意：释放锁前要判断当前是否被锁住了lock.isLocked()，否则之间调用unlock会报异常
                    // 严谨一点，防止当前线程释放掉其他线程的锁
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        } else {
            // 重试获取锁，幂等、防重校验、告警、日志记录、友好的提示等等
        }
        return couponResponse;
    }

    private static CouponActivityLog getCouponActivityLog(ReceiveCouponRequest request, Coupon coupon) {
        CouponActivityLog couponActivityLog = new CouponActivityLog();
        couponActivityLog.setCouponActivityId(request.getCouponActivityID());
        couponActivityLog.setCode(coupon.getCode());
        couponActivityLog.setUserId(request.getUserId());
        couponActivityLog.setMobile(request.getMobile());
        couponActivityLog.setCreateTime(new Date());
        couponActivityLog.setUpdateTime(new Date());
        return couponActivityLog;
    }

    /**
     * 判断是否可以参加活动
     *
     * @param couponActivity
     * @return
     */
    private boolean checkIsCanJoinActivity(CouponActivity couponActivity) {
        boolean available = checkActivityIsAvailable(couponActivity);
        if (available) {
            // 查询当前用户已领取数量 todo 优化改为查询Redis
            QueryWrapper<CouponActivityLog> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("coupon_activity_id", couponActivity.getId());
            Integer receivedNumber = couponActivityLogMapper.selectCount(queryWrapper);
            if (couponActivity.getLimitNumber() > receivedNumber) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断当前活动是否生效
     * 当前是活动状态是生效、当前时间在活动范围内、
     *
     * @param couponActivity
     * @return
     */
    private boolean checkActivityIsAvailable(CouponActivity couponActivity) {
        if (couponActivity.getStatus().equals(StatusEnum.AVAILABLE.getCode())
                && couponActivity.getBeginTime().before(new Date())
                && couponActivity.getEndTime().after(new Date())) {
            return true;
        }
        return false;
    }
}
