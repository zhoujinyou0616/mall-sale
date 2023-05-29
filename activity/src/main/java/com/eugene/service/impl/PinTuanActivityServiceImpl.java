package com.eugene.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.common.enums.PinTuanStatusEnum;
import com.eugene.controller.request.CreatePinTuanActivityRequest;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.mapper.PinTuanActivityMapper;
import com.eugene.pojo.PinTuanActivity;
import com.eugene.service.IPinTuanActivityService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/29 13:02
 */
@Service
public class PinTuanActivityServiceImpl extends ServiceImpl<PinTuanActivityMapper, PinTuanActivity> implements IPinTuanActivityService {

    @Override
    public String createPinTuanOrder(CreatePinTuanActivityRequest request) {
        Snowflake snowflake = new Snowflake();
        PinTuanActivity pinTuanActivity = new PinTuanActivity();
        pinTuanActivity.setActivityId(request.getActivityId());
        pinTuanActivity.setOrderNo(snowflake.nextIdStr());
        pinTuanActivity.setUserId(request.getUserId());
        pinTuanActivity.setMobile(request.getMobile());
        pinTuanActivity.setStatus(PinTuanStatusEnum.NO_PAY.getCode());
        pinTuanActivity.setCreateTime(new Date());
        pinTuanActivity.setUpdateTime(new Date());
        boolean save = save(pinTuanActivity);
        if (save) {
            return pinTuanActivity.getOrderNo();
        }
        return null;
    }

    @Override
    public PinTuanActivity getPinTuanActivityInfo(String activityId) {
        return getOne(new QueryWrapper<PinTuanActivity>().eq("activity_id", activityId));
    }

    @Override
    public String joinPinTuanActivity(JoinPinTuanActivityRequest request) {
        PinTuanActivity pinTuanActivity = new PinTuanActivity();
        pinTuanActivity.setActivityId(request.getActivityId());
        Snowflake snowflake = new Snowflake();
        String orderNo = snowflake.nextIdStr();
        pinTuanActivity.setOrderNo(orderNo);
        pinTuanActivity.setUserId(request.getUserId());
        pinTuanActivity.setMobile(request.getMobile());
        pinTuanActivity.setStatus(PinTuanStatusEnum.NO_PAY.getCode());
        pinTuanActivity.setCreateTime(new Date());
        pinTuanActivity.setUpdateTime(new Date());
        boolean save = save(pinTuanActivity);
        if (save) {
            return pinTuanActivity.getOrderNo();
        }
        return null;
    }
}
