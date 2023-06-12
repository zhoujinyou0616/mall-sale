package com.eugene.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eugene.common.enums.PinTuanStatusEnum;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.mapper.PinTuanActivityMapper;
import com.eugene.pojo.PinTuanActivity;
import com.eugene.service.IPinTuanActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/29 13:02
 */
@Service
public class PinTuanActivityServiceImpl extends ServiceImpl<PinTuanActivityMapper, PinTuanActivity> implements IPinTuanActivityService {

    @Override
    public void createPinTuanOrder(PinTuanActivity pinTuanActivity) {
        save(pinTuanActivity);
    }

    @Override
    public PinTuanActivity getPinTuanActivityInfo(Long pinTuanActivityId) {
        return getOne(new QueryWrapper<PinTuanActivity>().eq("id", pinTuanActivityId));
    }

    @Override
    public String joinPinTuanActivity(JoinPinTuanActivityRequest request) {
        PinTuanActivity pinTuanActivity = new PinTuanActivity();
        pinTuanActivity.setJoinPinTuanActivityId(request.getPinTuanActivityId());
        pinTuanActivity.setActivityId(request.getActivityId());
        Snowflake snowflake = new Snowflake();
        String orderNo = snowflake.nextIdStr();
        pinTuanActivity.setOrderNo(orderNo);
        pinTuanActivity.setSku(request.getSku());
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

    /**
     * 查询拼团订单数量，+1是因为要包括发起拼团订单数量
     *
     * @param pinTuanActivityId
     * @return
     */
    @Override
    public Integer getPinTuanOrderNumber(Long pinTuanActivityId) {
        return count(new QueryWrapper<PinTuanActivity>().eq("join_pin_tuan_activity_id", pinTuanActivityId)) + 1;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateActivityStatus(JoinPinTuanActivityRequest request, int code) {
        // 更新发起拼团订单状态
        PinTuanActivity pinTuanActivity = new PinTuanActivity();
        pinTuanActivity.setStatus(code);
        pinTuanActivity.setId(request.getPinTuanActivityId());
        updateById(pinTuanActivity);
        // 更新拼团订单的状态
        update(new UpdateWrapper<>(new PinTuanActivity()).set("status", code).eq("join_pin_tuan_activity_id", request.getPinTuanActivityId()));
    }
}
