package com.eugene.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/7 18:32
 */
@Data
public class AddCouponActivityRequest {

    @Schema(description = "活动名称", required = true)
    private String name;
    @Schema(description = "优惠券模版code", required = true)
    private String couponTemplateCode;
    @Schema(description = "券总数量 -1不限制", required = true)
    private Long totalNumber;
    @Schema(description = "每人可领取数量", required = true)
    private Long limitNumber;
    @Schema(description = "优惠券状态：0-不可用 1-可用", required = true)
    private Integer status;
    @Schema(description = "活动开始时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;
    @Schema(description = "活动结束时间", required = true)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
