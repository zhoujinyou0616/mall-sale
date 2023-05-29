package com.eugene.design.strategy;

import com.eugene.controller.request.AdminActivityRequest;
import com.eugene.pojo.Activity;
import org.springframework.stereotype.Service;

/**
 * @Description 拼团活动策略实现类
 * @Author eugene
 * @Data 2023/4/18 15:58
 */
@Service("activity_type_1")
public class PinTuanActivityStrategy implements IActivityStrategy {


    @Override
    public Activity getActivityStrategy() {
        return null;
    }

    @Override
    public boolean joinActivity(AdminActivityRequest request) {
        return false;
    }
}
