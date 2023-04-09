package com.eugene.service;

import com.eugene.pojo.Coupon;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Description 用户优惠券缓存相关接口
 * @Author eugene
 * @Data 2023/4/7 10:16
 */
public interface ICouponCacheService {


    /**
     * 保存优惠券缓存信息，设置失效时间为过期时间
     *
     * @param coupon
     * @return
     */
    boolean setCouponCache(Coupon coupon);

    /**
     * 保存优惠券缓存信息，设置指定的失效时间
     *
     * @param coupon
     * @return
     */
    boolean setCouponCache(Coupon coupon, Long time, TimeUnit timeUnit);

    /**
     * 查询优惠券缓存
     *
     * @param code
     * @return
     */
    Coupon getCouponCache(String code);


    /**
     * 批量查询优惠券缓存
     *
     * @param code
     * @return
     */
    List<Coupon> batchGetCouponCache(List<String> code);

    /**
     * 批量保存优惠券信息缓存，并设置券过期时间
     *
     * @param coupons
     * @return
     */
    boolean batchSetCouponCache(List<Coupon> coupons);

    /**
     * 查询用户拥有的优惠券列表
     *
     * @param mobile
     * @return
     */
    List<String> getUserCouponCodeList(Long mobile);

    /**
     * 添加用户拥有的优惠券列表
     *
     * @param mobile
     * @return
     */
    boolean addUserCouponCode(Long mobile, String couponCode);

    /**
     * 删除用户拥有的优惠券
     *
     * @param coupon
     * @return
     */
    boolean delUserCouponCode(Coupon coupon);

    /**
     * 删除优惠券信息
     *
     * @param coupon
     * @return
     */
    boolean delCouponCache(Coupon coupon);


}
