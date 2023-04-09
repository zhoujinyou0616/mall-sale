package com.eugene.cache.impl;

import cn.hutool.json.JSONUtil;
import com.eugene.cache.ICouponActivityCacheService;
import com.eugene.common.constant.CouponActivityKeyConstant;
import com.eugene.pojo.CouponActivity;
import com.eugene.utils.RedisUtil;
import jodd.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.eugene.common.constant.CouponCacheKeyConstant.getCouponActivityKey;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/7 18:54
 */
@Service
public class CouponActivityCacheServiceImpl implements ICouponActivityCacheService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void setCouponActivityCache(CouponActivity couponActivity) {
        Map<String, Object> couponActivityCacheMap = new HashMap<>();
        couponActivityCacheMap.put(CouponActivityKeyConstant.ACTIVITY_INFO, JSONUtil.toJsonStr(couponActivity));
        couponActivityCacheMap.put(CouponActivityKeyConstant.TOTAL_NUMBER, String.valueOf(couponActivity.getTotalNumber()));
        redisUtil.hmset(getCouponActivityKey(couponActivity.getId()), couponActivityCacheMap);
    }

    @Override
    public CouponActivity getCouponActivityCache(Long couponActivityId) {
        Map<String, Object> couponActivityCacheMap = redisUtil.hmget(getCouponActivityKey(couponActivityId));
        if (Objects.isNull(couponActivityCacheMap) || StringUtil.isBlank((String) couponActivityCacheMap.get(CouponActivityKeyConstant.ACTIVITY_INFO))) {
            return null;
        }
        CouponActivity couponActivityCache = JSONUtil.toBean((String) couponActivityCacheMap.get(CouponActivityKeyConstant.ACTIVITY_INFO), CouponActivity.class);
        Long totalNumber = Long.valueOf(String.valueOf(couponActivityCacheMap.get(CouponActivityKeyConstant.TOTAL_NUMBER)));
        CouponActivity couponActivity = new CouponActivity();
        couponActivity.setId(couponActivityCache.getId());
        couponActivity.setName(couponActivityCache.getName());
        couponActivity.setCouponTemplateCode(couponActivityCache.getCouponTemplateCode());
        couponActivity.setTotalNumber(totalNumber);
        couponActivity.setLimitNumber(couponActivityCache.getLimitNumber());
        couponActivity.setStatus(couponActivityCache.getStatus());
        couponActivity.setBeginTime(couponActivityCache.getBeginTime());
        couponActivity.setEndTime(couponActivityCache.getEndTime());
        couponActivity.setCreateTime(couponActivityCache.getCreateTime());
        couponActivity.setUpdateTime(couponActivityCache.getUpdateTime());
        return couponActivity;
    }

    @Override
    public void invalidateCouponActivityCache(Long couponActivityId) {
        redisUtil.del(getCouponActivityKey(couponActivityId));
    }
}
