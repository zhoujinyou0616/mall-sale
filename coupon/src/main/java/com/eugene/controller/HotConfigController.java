package com.eugene.controller;

import com.eugene.common.config.HotConfig;
import com.eugene.response.Response;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 热配操作类
 * @Author eugene
 * @Data 2023/4/5 14:56
 */
@RestController
@RequestMapping("/hotConfig")
public class HotConfigController {

    @Autowired
    private HotConfig hotConfig;

    @GetMapping("/getConfig")
    @Operation(summary = "查询config", description = "查询config")
    public Response getList() {
        return Response.success(hotConfig.getReadAndWriteSwitch());
    }

}
