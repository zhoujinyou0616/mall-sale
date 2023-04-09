package com.eugene.service;

import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.pojo.CouponTemplate;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/4 15:19
 */
public interface ICouponTemplateService {

    boolean addCouponTemplate(CouponTemplateRequest request);

    CouponTemplate getCouponTemplate(String couponTemplateCode);

}
