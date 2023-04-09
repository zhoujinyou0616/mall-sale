package com.eugene.service;

import com.eugene.common.exception.BusinessException;
import com.eugene.controller.request.AddCouponActivityRequest;
import com.eugene.controller.request.CouponActivityRequest;
import com.eugene.controller.request.ReceiveCouponRequest;
import com.eugene.controller.request.UserCouponRequest;
import com.eugene.controller.response.CouponActivityResponse;
import com.eugene.controller.response.CouponResponse;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/7 17:59
 */
public interface ICouponActivityService {

    boolean addCouponActivity(AddCouponActivityRequest request);

    List<CouponActivityResponse> getCouponCenterList(UserCouponRequest request);

    CouponActivityResponse getCouponActivityDetail(CouponActivityRequest request);

    CouponResponse receive(ReceiveCouponRequest request) throws BusinessException;
}
