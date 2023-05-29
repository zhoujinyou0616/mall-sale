package com.eugene.cache.impl;

import cn.hutool.json.JSONUtil;
import com.eugene.cache.IActivityCacheService;
import com.eugene.pojo.Activity;
import com.eugene.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.eugene.common.constant.RedisKeyConstant.getActivityInfoKey;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/27 20:43
 */
@Service
public class ActivityCacheServiceImpl implements IActivityCacheService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setActivityCache(String sku, Activity activity) {
        redisUtil.set(getActivityInfoKey(activity.getSku()), JSONUtil.toJsonStr(activity));
    }

    @Override
    public Activity getActivityCache(String sku) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(redisUtil.get(getActivityInfoKey(sku))), Activity.class);
    }

    @Override
    public void delActivityCache(String sku) {
        redisUtil.del(getActivityInfoKey(sku));
    }
}
