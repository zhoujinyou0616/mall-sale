package com.eugene.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.eugene.controller.request.CouponTemplateRequest;
import com.eugene.pojo.CouponTemplate;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/4 15:19
 */
public interface ICouponTemplateService extends IService<CouponTemplate> {

    boolean addCouponTemplate(CouponTemplateRequest request);

    CouponTemplate getCouponTemplate(String couponTemplateCode);

    boolean saveBatch(List<CouponTemplate> couponTemplateList);

}
