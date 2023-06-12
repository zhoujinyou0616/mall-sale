package com.eugene.common.constant;

/**
 * @Description Redis KEY
 * @Author eugene
 * @Data 2023/4/7 21:35
 */
public class RedisKeyConstant {

    public static final String ACTIVITY_INFO_KEY = "mall_activity:sku_%s";
    public static final String JOIN_PIN_TUAN_ACTIVITY_KEY = "mall_activity:join_pin_tuan_activity_%s_%s";

    public static String getActivityInfoKey(String sku) {
        return String.format(ACTIVITY_INFO_KEY, sku);
    }


    public static String getJoinPinTuanActivityKey(Long userId, Long pinTuanActivityId) {
        return String.format(JOIN_PIN_TUAN_ACTIVITY_KEY, userId, pinTuanActivityId);
    }
}
