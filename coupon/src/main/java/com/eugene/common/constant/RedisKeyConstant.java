package com.eugene.common.constant;

/**
 * @Description Redisson 分布式锁 KEY
 * @Author eugene
 * @Data 2023/4/7 21:35
 */
public class RedisKeyConstant {

    public static final String RECEIVE_COUPON_KEY = "mall_coupon:receive:userId_%s:%s";

    private static final String IDEMPOTENT_KEY = "mall_coupon:idempotent:register_%s";

    public static final String COUPON_TEMPLATE_KEY = "mall_coupon:couponTemplateCode:%s";


    public static String getReceiveCouponKey(Long userId, Long activityId) {
        return String.format(RECEIVE_COUPON_KEY, userId, activityId);
    }

    public static String getIdempotentKey(String key) {
        return String.format(IDEMPOTENT_KEY, key);
    }


    public static String getCouponTemplateCacheKey(String couponTemplateCode) {
        return String.format(COUPON_TEMPLATE_KEY, couponTemplateCode);
    }
}
