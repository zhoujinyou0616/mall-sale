package com.eugene.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/5/9 12:16
 */
@Data
public class JoinPinTuanActivityRequest {

    @NotEmpty(message = "活动商品sku不能为空")
    @Schema(description = "活动商品sku", required = true)
    private String sku;

    @NotNull(message = "拼团活动id不能为空")
    @Schema(description = "拼团活动id", required = true)
    private Long pinTuanActivityId;

    @NotNull(message = "活动id不能为空")
    @Schema(description = "活动id", required = true)
    private Long activityId;

    @NotNull(message = "用户id不能为空")
    @Schema(description = "用户id", required = true)
    private Long userId;

    @NotEmpty(message = "用户手机号不能为空")
    @Schema(description = "用户手机号", required = true)
    private String mobile;


}

