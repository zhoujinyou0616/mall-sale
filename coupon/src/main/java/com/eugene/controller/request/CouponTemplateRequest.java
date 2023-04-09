package com.eugene.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @Description 优惠券模版请求类
 * @Author eugene
 * @Data 2023/4/4 14:57
 */
@Data
public class CouponTemplateRequest {

    @Schema(description = "优惠券名称", required = true)
    @NotBlank
    private String name;

    @Schema(description = "优惠券金额", required = true)
    @NotNull
    private Long price;

    @Schema(description = "优惠券使用门槛金额", required = true)
    @NotNull
    private Long limitNumber;

    @Schema(description = "使用限制SKU")
    private String limitSku;

    @Schema(description = "使用限制SPU")
    private String limitSpu;

    @Schema(description = "有效期类型：0-起止日期 1-有效天数", required = true)
    @NotNull(message = "有效期类型 不能为空")
    private Integer validityType;

    @Schema(description = "优惠券开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date beginTime;

    @Schema(description = "优惠券结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    @Schema(description = "有效天数")
    private Long validityDay;


}
