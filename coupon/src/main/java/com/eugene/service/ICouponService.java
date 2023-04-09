package com.eugene.service;

import com.eugene.controller.request.SendCouponRequest;
import com.eugene.controller.request.UserCouponRequest;
import com.eugene.controller.request.VerificationCouponRequest;
import com.eugene.controller.response.CouponResponse;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/5 14:59
 */
public interface ICouponService {

    List<CouponResponse> getList(UserCouponRequest request);

    Boolean send(SendCouponRequest request);

    CouponResponse getCoupon(String code);

    Boolean verification(VerificationCouponRequest request);
}
