package com.eugene.threads;

import com.eugene.pojo.CouponTemplate;
import com.eugene.service.ICouponTemplateService;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 22:33
 */
public class CheckCouponTemplateDataHandle implements Callable<List<String>> {
    private ICouponTemplateService couponTemplateService;
    private List<CouponTemplate> couponTemplates;

    public CheckCouponTemplateDataHandle(ICouponTemplateService couponTemplateService, List<CouponTemplate> couponTemplates) {
        this.couponTemplateService = couponTemplateService;
        this.couponTemplates = couponTemplates;
    }


    @Override
    public List<String> call() throws Exception {
        // todo 对比当前新服务内数据
        // todo 不一致进行修复
        return null;
    }
}
