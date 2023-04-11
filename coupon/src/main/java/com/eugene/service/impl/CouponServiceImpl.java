package com.eugene.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.cache.ICouponTemplateCacheService;
import com.eugene.common.enums.CouponStatusEnum;
import com.eugene.common.enums.StatusEnum;
import com.eugene.common.enums.ValidityTypeEnum;
import com.eugene.common.exception.BusinessException;
import com.eugene.controller.request.SendCouponRequest;
import com.eugene.controller.request.UserCouponRequest;
import com.eugene.controller.request.VerificationCouponRequest;
import com.eugene.controller.response.CouponResponse;
import com.eugene.mapper.CouponMapper;
import com.eugene.pojo.Coupon;
import com.eugene.pojo.CouponTemplate;
import com.eugene.service.ICouponCacheService;
import com.eugene.service.ICouponService;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.eugene.common.enums.Errors.NOT_COUPON_INFO_ERROR;
import static com.eugene.controller.response.CouponResponse.buildCouponResponse;
import static com.eugene.utils.CouponUtil.getCouponCode;

/**
 * @Description 用户优惠券相关实现类
 * @Author eugene
 * @Data 2023/4/5 15:01
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {
    private static final Logger log = LoggerFactory.getLogger(CouponServiceImpl.class);

    @Autowired
    private CouponMapper couponMapper;
    @Autowired
    private ICouponTemplateCacheService couponTemplateCacheService;
    @Autowired
    private ICouponCacheService couponCacheService;
    @Autowired
    private RedissonClient redissonClient;


    @Override
    public List<CouponResponse> getList(UserCouponRequest request) {
        // 查询用户拥有的优惠券Code列表
        List<String> couponCodeList = couponCacheService.getUserCouponCodeList(request.getMobile());
        // 根据优惠券Code去查询对应的优惠券信息
        List<Coupon> couponCacheList = couponCacheService.batchGetCouponCache(couponCodeList);
        // 组装优惠券结果返回
        return couponCacheList.stream().map(CouponResponse::buildCouponResponse).collect(Collectors.toList());
    }

    // TODO 优化改为多线程自动发券
    @Override
    public Boolean send(SendCouponRequest request) {
        // 优化：券模版信息放到Guava缓存中提高性能
        CouponTemplate couponTemplateCache = couponTemplateCacheService.getCouponTemplateCache(request.getCouponTemplateCode());
        if (Objects.isNull(couponTemplateCache)) {
            log.error("券模版信息不存在");
            return false;
        }
        // 自动发放优惠券券功能是程序配置调用，所以不需要校验券数量，直接发送
        List<Coupon> coupons = new ArrayList<>();
        for (int i = 0; i < request.getNumber(); i++) {
            coupons.add(getCoupon(request, couponTemplateCache));
            // todo 解决方案：解决code重复问题
//            coupons.add(getCoupon(request, couponTemplateCache, i));
        }
        try {
            couponMapper.saveBatch(coupons);
        } catch (Exception e) {
            log.error("保存DB优惠券信息失败", e);
            return false;
        }
        // 遍历保存，存在原子性问题
/*      for (int i = 0; i < request.getNumber(); i++) {
            Coupon coupon = coupons.get(i);
        // 保存优惠券信息
            couponCacheService.setCouponCache(coupon);
        // 保存用户拥有的优惠券列表
            couponCacheService.addUserCouponCode(request.getMobile(), coupon.getCode());
        }
 */
        // 优化：优惠券信息保存到Redis中,同一批次一起保存，保障数据的准确性
        boolean redisResult = couponCacheService.batchSetCouponCache(coupons);
        if (!redisResult) {
            // 告警通知、邮件、钉钉、等等
            log.warn("优惠券缓存保存失败, coupons:{}", coupons);
        }
        return true;
    }

    private static Coupon getCoupon(SendCouponRequest request, CouponTemplate couponTemplateCache) {
        Coupon coupon = new Coupon();
        coupon.setCouponTemplateCode(request.getCouponTemplateCode());
        coupon.setCode(getCouponCode(couponTemplateCache.getId()));
        coupon.setUserId(request.getUserId());
        coupon.setMobile(request.getMobile());
        coupon.setStatus(CouponStatusEnum.AVAILABLE.getCode());
        if (ValidityTypeEnum.isDeadline(couponTemplateCache.getValidityType())) {
            coupon.setBeginTime(couponTemplateCache.getBeginTime());
            coupon.setEndTime(couponTemplateCache.getEndTime());
        } else if (ValidityTypeEnum.isEffectiveDay(couponTemplateCache.getValidityType())) {
            // 计算有效天数
            Long validityDay = couponTemplateCache.getValidityDay();
            Date currentDate = new Date();
            coupon.setBeginTime(currentDate);
            coupon.setEndTime(DateUtil.offsetDay(currentDate, Math.toIntExact(validityDay)));
        }
        coupon.setCreateTime(new Date());
        coupon.setUpdateTime(new Date());
        return coupon;
    }

    @Override
    public CouponResponse getCoupon(String code) {
        CouponResponse response = null;
        Coupon couponCache = couponCacheService.getCouponCache(code);
        // todo 重点关注：缓存穿透问题，直接返回不查询数据库
        if (Objects.nonNull(couponCache) && couponCache.getCode().equals("default")) {
            // 命中了自定义的缓存Key,直接返回
            return null;
        }
        if (Objects.isNull(couponCache)) {
            // 加锁，todo 加锁KEY
            RLock lock = redissonClient.getLock(code);
            if (lock.tryLock()) {
                try {
                    couponCache = getCouponDB(code);
                } finally {
                    if (lock.isLocked()) {
                        // 严谨一点，防止当前线程释放掉其他线程的锁
                        if (lock.isHeldByCurrentThread()) {
                            lock.unlock();
                        }
                    }
                }
            }
        }
        if (Objects.nonNull(couponCache)) {
            response = buildCouponResponse(couponCache);
        }
        return response;
    }


    @SneakyThrows
    @Override
    public Boolean verification(VerificationCouponRequest request) {
        // 加锁
        RLock lock = redissonClient.getLock(request.getCouponCode());
        if (lock.tryLock()) {
            try {
                List<String> userCouponCodeList = couponCacheService.getUserCouponCodeList(request.getMobile());
                // 风控校验：判断当前用户和优惠券是否是同一人，优惠券是否可用
                if (!userCouponCodeList.contains(request.getCouponCode())) {
                    throw new BusinessException(NOT_COUPON_INFO_ERROR.getCode(), NOT_COUPON_INFO_ERROR.getMsg());
                }
                Coupon couponCache = couponCacheService.getCouponCache(request.getCouponCode());
                // 检查用户和优惠券是否可用
                if (checkIsCanVerification(request, couponCache)) {
                    Coupon coupon = new Coupon();
                    coupon.setId(couponCache.getId());
                    coupon.setCode(couponCache.getCode());
                    coupon.setMobile(couponCache.getMobile());
                    coupon.setStatus(CouponStatusEnum.USED.getCode());
                    coupon.setUseTime(new Date());
                    coupon.setUpdateTime(new Date());
                    // todo 因为分片键mobile不能更新，mobile暂时设置为null, 后续优化
                    coupon.setMobile(null);
                    // todo 改为MQ异步更新Mysql，增加消息不丢失机制
                    int result = couponMapper.updateById(coupon);
                    if (result > 0) {
                        // 核销优惠券信息
                        couponCacheService.delUserCouponCode(coupon);
                        couponCacheService.delCouponCache(coupon);
                    }
                    return result > 0;
                } else {
                    throw new BusinessException(NOT_COUPON_INFO_ERROR.getCode(), NOT_COUPON_INFO_ERROR.getMsg());
                }
            } finally {
                if (lock.isLocked()) {
                    // 严谨一点，防止当前线程释放掉其他线程的锁
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        }
        return false;
    }

    private boolean checkIsCanVerification(VerificationCouponRequest request, Coupon couponCache) throws BusinessException {
        if (Objects.isNull(couponCache)) {
            throw new BusinessException(NOT_COUPON_INFO_ERROR.getCode(), NOT_COUPON_INFO_ERROR.getMsg());
        }
        // 检查是不是本人核销优惠券、是否在可用时间范围内
        if (couponCache.getMobile().equals(request.getMobile())
                && couponCache.getBeginTime().before(new Date())
                && couponCache.getEndTime().after(new Date())) {
            return true;
        }
        return false;
    }

    private Coupon getCouponDB(String code) {
        Coupon couponCache;
        QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("code", code);
        queryWrapper.eq("status", StatusEnum.AVAILABLE.getCode());
        Coupon coupon = couponMapper.selectOne(queryWrapper);
        // 如果为空，则发生缓存穿透问题
        if (Objects.isNull(coupon)) {
            // 设置默认值default，过期时间1分钟, 缓存雪崩怎么解决啊？过期时间改为随机数就好了
            couponCacheService.setCouponCache(new Coupon("default"), 1L, TimeUnit.MINUTES);
            return null;
        } else {
            couponCacheService.setCouponCache(coupon);
            couponCache = coupon;
        }
        return couponCache;
    }

}
