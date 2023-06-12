package com.eugene.service;

import com.eugene.controller.request.AdminActivityRequest;
import com.eugene.controller.response.AdminActivityResponse;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/18 15:42
 */
public interface IAdminActivityService {

    List<AdminActivityResponse> getActivityInfoList();

    Long createActivity(AdminActivityRequest request);

    AdminActivityResponse getActivityInfo(Long activityId);


}
