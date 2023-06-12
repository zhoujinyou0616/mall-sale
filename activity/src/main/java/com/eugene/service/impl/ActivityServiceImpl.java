package com.eugene.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.cache.IActivityCacheService;
import com.eugene.common.enums.Errors;
import com.eugene.common.enums.PinTuanStatusEnum;
import com.eugene.common.exception.BusinessException;
import com.eugene.common.vo.PinTuanActivityRule;
import com.eugene.controller.request.CreatePinTuanActivityRequest;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.controller.response.ActivityResponse;
import com.eugene.controller.response.PinTuanActivityResponse;
import com.eugene.mapper.ActivityMapper;
import com.eugene.mq.producer.PinTuanActivityProducerService;
import com.eugene.pojo.Activity;
import com.eugene.pojo.PinTuanActivity;
import com.eugene.service.IActivityService;
import com.eugene.service.IPinTuanActivityService;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eugene.common.constant.RedisKeyConstant.getJoinPinTuanActivityKey;
import static com.eugene.pojo.PinTuanActivity.convertPinTuanActivity;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/18 15:44
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements IActivityService {
    private static final Logger log = LoggerFactory.getLogger(ActivityServiceImpl.class);
    @Autowired
    private IActivityCacheService activityCacheService;
    @Autowired
    private IPinTuanActivityService pinTuanActivityService;
    @Autowired
    private PinTuanActivityProducerService pinTuanActivityProducerService;
    @Autowired
    private RedissonClient redissonClient;

    @Override
    public ActivityResponse getActivityInfoBySku(String sku) {
        // 查询缓存中的活动信息,
        Activity activityCache = activityCacheService.getActivityCache(sku);
        if (activityCache != null) {
            return ActivityResponse.convertActivityResponse(activityCache);
        }
        return null;
    }

    @Override
    @SneakyThrows
    public Long createPinTuanActivity(CreatePinTuanActivityRequest request) {
        // 1.检查请求参数、活动能否参与、商品是否存在等..
        if (Boolean.FALSE.equals(checkCanCreateActivity(request))) {
            throw new BusinessException(Errors.ACTIVITY_CAN_NOT_CREATE_ERROR.getCode(), Errors.ACTIVITY_CAN_NOT_CREATE_ERROR.getMsg());
        }
        // 2.创建拼团订单
        return createPinTuanOrder(request);
    }

    @Override
    public PinTuanActivityResponse getPinTuanActivityInfo(Long pinTuanActivityId) {
        PinTuanActivity pinTuanActivityInfo = pinTuanActivityService.getPinTuanActivityInfo(pinTuanActivityId);
        if (pinTuanActivityInfo != null) {
            return PinTuanActivityResponse.convertPinTuanActivityResponse(pinTuanActivityInfo);
        }
        return null;
    }

    @SneakyThrows
    @Override
    public String joinPinTuanActivity(JoinPinTuanActivityRequest request) {
        if (Boolean.FALSE.equals(checkCanJoinActivity(request))) {
            throw new BusinessException(Errors.ACTIVITY_CAN_NOT_CREATE_ERROR.getCode(), Errors.ACTIVITY_CAN_NOT_CREATE_ERROR.getMsg());
        }
        // 加锁，防止多个参团人同时参加
        RLock lock = redissonClient.getLock(getJoinPinTuanActivityKey(request.getUserId(), request.getPinTuanActivityId()));
        if (lock.tryLock()) {
            String orderNo = pinTuanActivityService.joinPinTuanActivity(request);
            try {
                // todo 后续开发订单中心项目，将订单信息推送到订单中心

                // 检查拼团是否结束，更新拼团活动状态为已成团，
                if (checkPinTuanIsFinished(request) == Boolean.TRUE) {
                    pinTuanActivityService.updateActivityStatus(request, PinTuanStatusEnum.SUCCESS.getCode());
                }
                return orderNo;
            } finally {
                if (lock.isLocked()) {
                    // 严谨一点，防止当前线程释放掉其他线程的锁
                    if (lock.isHeldByCurrentThread()) {
                        lock.unlock();
                    }
                }
            }
        } else {
            // 重试获取锁，幂等、防重校验、告警、日志记录、友好的提示等等
        }
        return null;
    }

    /**
     * 检查拼团活动是否成团
     *
     * @param request
     * @return
     */
    private boolean checkPinTuanIsFinished(JoinPinTuanActivityRequest request) {
        Activity activityCache = activityCacheService.getActivityCache(request.getSku());
        PinTuanActivityRule pinTuanActivityRule = JSONUtil.toBean(activityCache.getRule(), PinTuanActivityRule.class);
        Integer pinTuanOrderNumber = pinTuanActivityService.getPinTuanOrderNumber(request.getPinTuanActivityId());
        return pinTuanActivityRule.getNumber().equals(pinTuanOrderNumber);
    }


    private Long createPinTuanOrder(CreatePinTuanActivityRequest request) {
        Activity activityCache = activityCacheService.getActivityCache(request.getSku());
        PinTuanActivity pinTuanActivity = convertPinTuanActivity(request, activityCache.getId());
        // 创建拼团订单
        pinTuanActivityService.createPinTuanOrder(pinTuanActivity);
        // 发送拼团结束的延迟消息，24小时
        pinTuanActivityProducerService.delaySendMessage(pinTuanActivity);
        return pinTuanActivity.getId();
    }

    /**
     * 1.检查请求参数、活动能否参与、商品是否存在等..
     *
     * @param request
     * @return
     */
    private Boolean checkCanCreateActivity(CreatePinTuanActivityRequest request) {
        // todo 检查请求参数、活动能否参与、商品是否存在、拼团活动的能否发起、是否已经成团等..
        return true;
    }


    private Boolean checkCanJoinActivity(JoinPinTuanActivityRequest request) {
        // todo 检查活动是否有效，活动能否参与、活动是否已经成团、是否已经参与过等..
        return true;
    }

}
