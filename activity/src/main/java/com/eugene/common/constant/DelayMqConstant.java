package com.eugene.common.constant;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/26 14:32
 */
public class DelayMqConstant {

    /**
     * 延时队列代理的topic
     */
    public static final String DELAY_TOPIC = "DELAY_TOPIC";

    /**
     * 延时队列代理的topic
     */
    public static final String DELAY_GROUP = "DELAY_GROUP";

    /**
     * 延时队列时间轮的时间间隔
     */
    public static final Long WHEEL_TIME = 60L;

    public static String getDelayTopicTag() {
        return DELAY_TOPIC + ":" + DELAY_GROUP;
    }
}
