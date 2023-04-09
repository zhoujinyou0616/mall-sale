package com.eugene.common.constant;

/**
 * @Description Redisson 分布式锁 KEY
 * @Author eugene
 * @Data 2023/4/7 21:35
 */
public class RedisKeyConstant {

    public static final String RECEIVE_COUPON_KEY = "mall_coupon:receive:userId_%s:%s";


    public static String getReceiveCouponKey(Long userId, Long activityId) {
        return String.format(RECEIVE_COUPON_KEY, userId, activityId);
    }
}
