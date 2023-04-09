package com.eugene.pojo;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eugene.common.enums.CouponStatusEnum;
import com.eugene.common.enums.ValidityTypeEnum;
import com.eugene.controller.request.ReceiveCouponRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static com.eugene.utils.CouponUtil.getCouponCode;

/**
 * @Description 用户优惠券
 * @Author eugene
 * @Data 2023/4/4 14:32
 */
@Data
@TableName("t_coupon")
@EqualsAndHashCode
public class Coupon implements Serializable {
    private static final long serialVersionUID = -145234232599455037L;

    @TableId(type = IdType.AUTO)
    @Schema(description = "id", required = true)
    private Long id;
    @Schema(description = "优惠券模版code", required = true)
    private String couponTemplateCode;
    @Schema(description = "用户优惠券code", required = true)
    private String code;
    @Schema(description = "用户id", required = true)
    private Long userId;
    @Schema(description = "手机号", required = true)
    private Long mobile;
    @Schema(description = "优惠券状态：0-不可用 1-可用 2-已使用 3-已过期", required = true)
    private Integer status;
    @Schema(description = "优惠券开始时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @Schema(description = "优惠券结束时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Schema(description = "优惠券领取时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Schema(description = "优惠券使用时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date useTime;
    @Schema(description = "更新时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public Coupon() {
    }

    public Coupon(String couponCode) {
        this.code = couponCode;
    }

    public static Coupon buildCoupon(ReceiveCouponRequest request, CouponTemplate CouponTemplate) {
        Coupon coupon = new Coupon();
        coupon.setCouponTemplateCode(CouponTemplate.getCode());
        coupon.setCode(getCouponCode(CouponTemplate.getId()));
        coupon.setUserId(request.getUserId());
        coupon.setMobile(request.getMobile());
        coupon.setStatus(CouponStatusEnum.AVAILABLE.getCode());
        if (ValidityTypeEnum.isDeadline(CouponTemplate.getValidityType())) {
            coupon.setBeginTime(CouponTemplate.getBeginTime());
            coupon.setEndTime(CouponTemplate.getEndTime());
        } else if (ValidityTypeEnum.isEffectiveDay(CouponTemplate.getValidityType())) {
            // 计算有效天数
            Long validityDay = CouponTemplate.getValidityDay();
            Date currentDate = new Date();
            coupon.setBeginTime(currentDate);
            coupon.setEndTime(DateUtil.offsetDay(currentDate, Math.toIntExact(validityDay)));
        }
        coupon.setCreateTime(new Date());
        coupon.setUpdateTime(new Date());
        return coupon;
    }
}
