package com.eugene.controller.response;

import com.eugene.pojo.CouponActivity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 优惠券活动表
 * @Author eugene
 * @Data 2023/4/4 14:32
 */
@Data
public class CouponActivityResponse implements Serializable {

    @Schema(description = "id")
    private Long id;
    @Schema(description = "活动名称")
    private String name;
    @Schema(description = "优惠券模版code")
    private String couponTemplateCode;
    @Schema(description = "券总数量 -1不限制")
    private Long totalNumber;
    @Schema(description = "每人可领取数量")
    private Long limitNumber;
    @Schema(description = "优惠券状态：0-不可用 1-可用")
    private Integer status;
    @Schema(description = "当前已领取数量")
    private Long receivedNumber;
    @Schema(description = "活动开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @Schema(description = "活动结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public static CouponActivityResponse buildCouponActivityResponse(CouponActivity couponActivity, Long receivedNumber) {
        CouponActivityResponse response = new CouponActivityResponse();
        response.setId(couponActivity.getId());
        response.setName(couponActivity.getName());
        response.setCouponTemplateCode(couponActivity.getCouponTemplateCode());
        response.setTotalNumber(couponActivity.getTotalNumber());
        response.setLimitNumber(couponActivity.getLimitNumber());
        response.setStatus(couponActivity.getStatus());
        response.setReceivedNumber(receivedNumber);
        response.setBeginTime(couponActivity.getBeginTime());
        response.setEndTime(couponActivity.getEndTime());
        response.setCreateTime(couponActivity.getCreateTime());
        response.setUpdateTime(couponActivity.getUpdateTime());
        return response;
    }

}
