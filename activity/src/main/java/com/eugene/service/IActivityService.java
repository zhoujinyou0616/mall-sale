package com.eugene.service;

import com.eugene.controller.request.CreatePinTuanActivityRequest;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.controller.response.ActivityResponse;
import com.eugene.controller.response.PinTuanActivityResponse;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/5/8 22:04
 */
public interface IActivityService {

    ActivityResponse getActivityInfoBySku(String sku);

    Long createPinTuanActivity(CreatePinTuanActivityRequest request);

    PinTuanActivityResponse getPinTuanActivityInfo(Long pinTuanActivityId);

    String joinPinTuanActivity(JoinPinTuanActivityRequest request);
}
