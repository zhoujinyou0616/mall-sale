package com.eugene.service;

import com.eugene.controller.request.CouponTemplateRemoteRequest;
import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.pojo.CouponTemplate;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/4 15:19
 */
@Deprecated
public interface ICouponTemplateService {

    boolean addCouponTemplate(CouponTemplateRequest request);

    CouponTemplate getCouponTemplate(String couponTemplateCode);

    List<CouponTemplate> list(CouponTemplateRemoteRequest request);
}
