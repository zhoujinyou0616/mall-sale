package com.eugene.design.strategy;

import com.eugene.controller.request.AdminActivityRequest;
import com.eugene.pojo.Activity;

/**
 * @Description 活动策略模式，包含 秒杀、拼团、砍价、
 * @Author eugene
 * @Data 2023/4/18 15:55
 */
public interface IActivityStrategy {

    /**
     * 查询活动
     */
    Activity getActivityStrategy();

    /**
     * 参与活动
     *
     * @param request
     */
    boolean joinActivity(AdminActivityRequest request);
}
