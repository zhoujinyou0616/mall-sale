package com.eugene.service;

import com.eugene.controller.request.JoinPinTuanActivityRequest;
import com.eugene.pojo.PinTuanActivity;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/29 13:01
 */
public interface IPinTuanActivityService {
    void createPinTuanOrder(PinTuanActivity pinTuanActivity);


    PinTuanActivity getPinTuanActivityInfo(Long pinTuanActivityId);

    String joinPinTuanActivity(JoinPinTuanActivityRequest request);

    Integer getPinTuanOrderNumber(Long pinTuanActivityId);

    void updateActivityStatus(JoinPinTuanActivityRequest request, int code);
}
