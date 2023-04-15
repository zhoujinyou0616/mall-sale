package com.eugene.common.constant;

/**
 * @Description Redisson 分布式锁 KEY
 * @Author eugene
 * @Data 2023/4/7 21:35
 */
public class RedisKeyConstant {

    public static final String COUPON_TEMPLATE_KEY = "mall_coupon_new:couponTemplateCode:%s";


    public static String getCouponTemplateCacheKey(String couponTemplateCode) {
        return String.format(COUPON_TEMPLATE_KEY, couponTemplateCode);
    }

}
