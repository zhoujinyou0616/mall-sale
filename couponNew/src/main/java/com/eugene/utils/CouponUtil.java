package com.eugene.utils;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;

import java.util.Date;

/**
 * @Description 优惠券工具类
 * @Author eugene
 * @Data 2023/4/4 16:00
 */
@Configuration
@AllArgsConstructor
public class CouponUtil {

    public final static Snowflake snowflake = new Snowflake();

    /**
     * 生成优惠券模版Code
     * 规则：CP + 雪花算法ID
     *
     * @return
     */
    public static String getCouponTemplateCode() {
        return String.format("CP" + snowflake.nextId());
    }

    /**
     * 生成优惠券Code
     * 规则：UCP + 券模版id % 10 + 雪花算法ID
     * // todo 优化：如果仍然出现code重复，怎么解决？
     * 方案是：再后面追加一个发券发放张数（第几张）
     *
     * @return
     */
    public static String getCouponCode(Long couponTemplateId) {
        return String.format("UCP" + couponTemplateId % 10 + snowflake.nextId());
    }

    /**
     * 根据两个日期之间的时间差，计算优惠券的过期时间
     *
     * @param beginTime
     * @param endTime
     * @return 返回过期时间
     */
    public static long calcCouponExpireTime(Date beginTime, Date endTime) {
        return DateUtil.between(beginTime, endTime, DateUnit.SECOND);
    }

}
