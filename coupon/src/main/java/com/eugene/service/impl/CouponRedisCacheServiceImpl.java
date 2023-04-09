package com.eugene.service.impl;

import cn.hutool.json.JSONUtil;
import com.eugene.pojo.Coupon;
import com.eugene.service.ICouponCacheService;
import com.eugene.utils.CouponRedisLuaUtil;
import com.eugene.utils.RedisUtil;
import jodd.util.StringUtil;
import org.omg.CORBA.TIMEOUT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.eugene.common.constant.CouponCacheKeyConstant.getCouponKey;
import static com.eugene.common.constant.CouponCacheKeyConstant.getUserCouponKey;
import static com.eugene.utils.CouponUtil.calcCouponExpireTime;

/**
 * @Description 用户优惠券实现类
 * @Author eugene
 * @Data 2023/4/7 10:17
 */
@Service
public class CouponRedisCacheServiceImpl implements ICouponCacheService {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private CouponRedisLuaUtil couponRedisLuaUtil;

    @Override
    public boolean setCouponCache(Coupon coupon) {
        return redisUtil.set(getCouponKey(coupon.getCode()), JSONUtil.toJsonStr(coupon), calcCouponExpireTime(coupon.getBeginTime(), coupon.getEndTime()));
    }

    @Override
    public boolean setCouponCache(Coupon coupon, Long time, TimeUnit timeUnit) {
        return redisUtil.set(getCouponKey(coupon.getCode()), JSONUtil.toJsonStr(coupon), calcCouponExpireTime(coupon.getBeginTime(), coupon.getEndTime()));
    }

    @Override
    public Coupon getCouponCache(String code) {
        // todo 使用Guava缓存
        String result = (String) redisUtil.get(getCouponKey(code));
        return StringUtil.isNotEmpty(result) ? JSONUtil.toBean(result, Coupon.class) : null;
    }

    @Override
    public List<Coupon> batchGetCouponCache(List<String> codes) {
        return codes.stream()
                .map(code -> JSONUtil.toBean((String) redisUtil.get(getCouponKey(code)), Coupon.class))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public boolean batchSetCouponCache(List<Coupon> coupons) {
        return couponRedisLuaUtil.batchSet(coupons);
    }

    @Override
    public List<String> getUserCouponCodeList(Long mobile) {
        return redisUtil.lGet(
                getUserCouponKey(mobile),
                0,
                500
        );
    }

    @Override
    public boolean addUserCouponCode(Long mobile, String couponCode) {
        return redisUtil.lSet(getUserCouponKey(mobile), couponCode);
    }

    @Override
    public boolean delUserCouponCode(Coupon coupon) {
        return redisUtil.lRemove(getUserCouponKey(coupon.getMobile()), 0L, coupon.getCode()) > 0;
    }

    @Override
    public boolean delCouponCache(Coupon coupon) {
        return redisUtil.del(getCouponKey(coupon.getCode())) > 0;
    }

}
