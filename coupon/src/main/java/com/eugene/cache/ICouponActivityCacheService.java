package com.eugene.cache;

import com.eugene.pojo.CouponActivity;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/7 18:53
 */
public interface ICouponActivityCacheService {

    void setCouponActivityCache(CouponActivity couponActivity);

    CouponActivity getCouponActivityCache(Long couponActivityId);

    void invalidateCouponActivityCache(Long couponActivityId);
}
