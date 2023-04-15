package com.eugene.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

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

    @Schema(description = "券模版code列表")
    private List<String> codes;


    @Schema(description = "限制条数")
    private Long limitSize;
}
