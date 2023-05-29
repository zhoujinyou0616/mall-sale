package com.eugene.service;

import com.eugene.controller.request.CreatePinTuanActivityRequest;
import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.pojo.PinTuanActivity;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/29 13:01
 */
public interface IPinTuanActivityService {
    String createPinTuanOrder(CreatePinTuanActivityRequest request);


    PinTuanActivity getPinTuanActivityInfo(String activityId);

    String joinPinTuanActivity(JoinPinTuanActivityRequest request);

}
