package com.eugene.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.cache.IActivityCacheService;
import com.eugene.common.enums.Errors;
import com.eugene.common.exception.BusinessException;
import com.eugene.controller.request.CreatePinTuanActivityRequest;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.controller.response.ActivityResponse;
import com.eugene.controller.response.PinTuanActivityResponse;
import com.eugene.mapper.ActivityMapper;
import com.eugene.pojo.Activity;
import com.eugene.pojo.PinTuanActivity;
import com.eugene.service.IActivityService;
import com.eugene.service.IPinTuanActivityService;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public String createPinTuanActivity(CreatePinTuanActivityRequest request) {
        // 1.检查请求参数、活动能否参与、商品是否存在等..
        if (Boolean.FALSE.equals(checkCanCreateActivity(request))) {
            throw new BusinessException(Errors.ACTIVITY_CAN_NOT_CREATE_ERROR.getCode(), Errors.ACTIVITY_CAN_NOT_CREATE_ERROR.getMsg());
        }
        // 2.创建拼团订单
        return createPinTuanOrder(request);
    }

    @Override
    public PinTuanActivityResponse getPinTuanActivityInfo(String activityId) {
        PinTuanActivity pinTuanActivityInfo = pinTuanActivityService.getPinTuanActivityInfo(activityId);
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
        String orderNo = pinTuanActivityService.joinPinTuanActivity(request);
        // 拼团成功，更新拼团活动状态为已成团
//        pinTuanActivityService.updateActivityStatus(request.getActivityId(), PinTuanStatusEnum.SUCCESS.getCode());
        // todo 将订单信息推送到订单中心
        return orderNo;
    }


    private String createPinTuanOrder(CreatePinTuanActivityRequest request) {
        // 创建拼团订单
        String orderNo = pinTuanActivityService.createPinTuanOrder(request);
        // todo 发送拼团结束的延迟消息，24小时

        return orderNo;
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
