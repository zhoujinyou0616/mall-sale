package com.eugene.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/5 15:14
 */
@Data
public class VerificationCouponRequest {

    @Schema(description = "用户id", required = true)
    @NotNull
    private Long userId;

    @Schema(description = "手机号", required = true)
    @NotNull
    private Long mobile;

    @Schema(description = "优惠券code", required = true)
    @NotNull
    private String couponCode;

}
