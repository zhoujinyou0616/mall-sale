package com.eugene.common.constant;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/12 11:25
 */
public class RocketMqConstant {

    // 用户系统 topic
    public static final String MALL_USER_TOPIC = "MALL_USER_TOPIC";

    // 用户注册发券 tag
    public static final String REGISTER_TAG = "REGISTER_TAG";

    // 用户注册 事务组
    public static final String TX_REGISTER_TAG = "TX_REGISTER_TAG";


    /**
     * 获取用户注册发券topic:tag
     *
     * @return
     */
    public static String getRegisterTopicTag() {
        return MALL_USER_TOPIC + ":" + REGISTER_TAG;
    }

    /**
     * 获取用户注册发券topic:tag 事务
     *
     * @return
     */
    public static String getTxRegisterTopicTag() {
        return MALL_USER_TOPIC + ":" + TX_REGISTER_TAG;
    }


}
