package com.eugene.utils;

import cn.hutool.json.JSONUtil;
import com.eugene.common.constant.CouponActivityKeyConstant;
import com.eugene.controller.request.ReceiveCouponRequest;
import com.eugene.pojo.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.eugene.common.constant.CouponCacheKeyConstant.*;
import static com.eugene.utils.CouponUtil.calcCouponExpireTime;

/**
 * redis工具类
 */
@Component
public class CouponRedisLuaUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 批量保存优惠券信息缓存，并设置券过期时间
     * todo 这里有两个不同的Key，所以操作了两次，后续需要优化成一次执行
     *
     * @param coupons
     * @return
     */
    public boolean batchSet(List<Coupon> coupons) {
        List<String> couponKeys = new ArrayList<>();
        List<String> userCouponKeys = new ArrayList<>();
        StringBuffer couponScript = new StringBuffer();
        StringBuffer userCouponScript = new StringBuffer();
        for (int i = 1; i <= coupons.size(); i++) {
            Coupon coupon = coupons.get(i - 1);
            // 1.保存用户优惠券信息缓存
            couponScript.append("redis.call('setnx', KEYS[" + i + "], '" + JSONUtil.toJsonStr(coupon) + "')");
            // 2.设置优惠券信息过期时间
            couponScript.append("redis.call('expire', KEYS[" + i + "], '" + calcCouponExpireTime(new Date(), coupon.getEndTime()) + "')");
            couponKeys.add(String.format(COUPON_KEY, coupon.getCode()));
            // 3.保存用户拥有的优惠券列表
            userCouponScript.append("redis.call('lpush', KEYS[" + i + "], '" + coupon.getCode() + "')");
            userCouponKeys.add(String.format(USER_COUPON_KEY, coupon.getMobile()));
        }
        DefaultRedisScript couponRedisScript = new DefaultRedisScript();
        couponRedisScript.setScriptText(couponScript.toString());
        DefaultRedisScript userCouponRedisScript = new DefaultRedisScript();
        userCouponRedisScript.setScriptText(userCouponScript.toString());
        try {
            redisTemplate.execute(couponRedisScript, couponKeys);
            redisTemplate.execute(userCouponRedisScript, userCouponKeys);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public boolean receive(ReceiveCouponRequest request) {
        // 领券分多步操作
        //1.扣减领券活动库存，
        // 如果失败，则回滚扣减领券活动库存
        // 如果成功 返回最新库存数量
        String script = "local newTotalNumber = redis.call('HINCRBY', KEYS[1], KEYS[2], -ARGV[1]); " +
                "if (newTotalNumber < 0) then " +
                "redis.call('HINCRBY', KEYS[1], KEYS[2], ARGV[1]); return -1; " +
                "else return newTotalNumber end;";
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript();
        redisScript.setScriptText(script);
        List<String> keys = new ArrayList<>();
        keys.add(getCouponActivityKey(request.getCouponActivityID()));
        keys.add(CouponActivityKeyConstant.TOTAL_NUMBER);
        redisScript.setResultType(Long.class);
        Long totalNumber = (Long) redisTemplate.execute(redisScript, keys, String.valueOf(request.getNumber()));
        return totalNumber != null && totalNumber > -1;


    }


}