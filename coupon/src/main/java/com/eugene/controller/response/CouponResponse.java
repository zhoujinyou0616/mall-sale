package com.eugene.controller.response;

import com.eugene.common.enums.CouponStatusEnum;
import com.eugene.pojo.Coupon;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户优惠券
 * @Author eugene
 * @Data 2023/4/4 14:32
 */
@Data
public class CouponResponse implements Serializable {

    private static final long serialVersionUID = -8291797299937978757L;

    @Schema(description = "优惠券模版code")
    private String coupon_template_code;
    @Schema(description = "用户优惠券code")
    private String code;
    @Schema(description = "用户id")
    private Long userId;
    @Schema(description = "手机号")
    private Long mobile;
    @Schema(description = "优惠券状态：0-不可用 1-可用 2-已使用 3-已过期")
    private Integer status;
    @Schema(description = "优惠券开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @Schema(description = "优惠券结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Schema(description = "优惠券领取时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Schema(description = "优惠券使用时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useTime;

    /**
     * 组装优惠券结果返回
     *
     * @param coupon
     * @return
     */
    public static CouponResponse buildCouponResponse(Coupon coupon) {
        CouponResponse response = new CouponResponse();
        response.setCoupon_template_code(coupon.getCouponTemplateCode());
        response.setCode(coupon.getCode());
        response.setUserId(coupon.getUserId());
        response.setMobile(coupon.getMobile());
        // 检查优惠券状态是否不可用
        response.setStatus(checkIsNotAvailable(coupon.getBeginTime()) ?
                Integer.valueOf(CouponStatusEnum.NOT_AVAILABLE.getCode()) : coupon.getStatus());
        response.setBeginTime(coupon.getBeginTime());
        response.setEndTime(coupon.getEndTime());
        response.setCreateTime(coupon.getCreateTime());
        response.setUseTime(coupon.getUseTime());
        return response;
    }

    /**
     * 校验优惠券是否不可用
     *
     * @param beginTime
     * @return
     */
    private static boolean checkIsNotAvailable(Date beginTime) {
        return beginTime.after(new Date());
    }

}
