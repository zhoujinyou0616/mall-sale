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
public class ReceiveCouponRequest {

    @Schema(description = "用户id", required = true)
    @NotNull(message = "user is not null")
    private Long userId;

    @Schema(description = "手机号", required = true)
    @NotNull(message = "mobile is not null")
    private Long mobile;

    @Schema(description = "券活动Id", required = true)
    @NotNull(message = "券活动Id is not null")
    private Long couponActivityID;

    @Schema(description = "优惠券数量")
    private Integer number = 1;


}
