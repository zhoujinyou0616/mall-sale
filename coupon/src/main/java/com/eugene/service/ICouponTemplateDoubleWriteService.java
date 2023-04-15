package com.eugene.service;

import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.pojo.CouponTemplate;

/**
 * @Description 双写阶段接口
 * @Author eugene
 * @Data 2023/4/4 15:19
 */
public interface ICouponTemplateDoubleWriteService {

    boolean addCouponTemplateDoubleWrite(CouponTemplateRequest request);

    CouponTemplate getCouponTemplateDoubleWrite(String couponTemplateCode);

}
