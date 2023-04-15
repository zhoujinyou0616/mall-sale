package com.eugene.remote.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 22:45
 */
@Data
public class CouponTemplateRemoteRequest {

    @Schema(description = "开始id")
    private Long startId = 1L;

    @Schema(description = "截止时间")
    private String limitTime;

    @Schema(description = "限制条数")
    private Long limitSize;
}
