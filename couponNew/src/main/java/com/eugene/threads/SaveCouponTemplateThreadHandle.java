package com.eugene.threads;

import com.eugene.pojo.CouponTemplate;
import com.eugene.service.ICouponTemplateService;

import java.util.List;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 22:09
 */
public class SaveCouponTemplateThreadHandle implements Runnable {
    private ICouponTemplateService couponTemplateService;
    private List<CouponTemplate> couponTemplateList;


    public SaveCouponTemplateThreadHandle(ICouponTemplateService couponTemplateService, List<CouponTemplate> couponTemplateList) {
        this.couponTemplateService = couponTemplateService;
        this.couponTemplateList = couponTemplateList;
    }

    @Override
    public void run() {
        // 批量保存
        couponTemplateService.saveBatch(couponTemplateList);
    }
}
