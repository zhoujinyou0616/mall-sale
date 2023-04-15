package com.eugene.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.eugene.response.Response;
import com.eugene.service.IAdminService;
import com.eugene.service.ICouponTemplateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @Description 管理员操作类
 * @Author eugene
 * @Data 2023/4/4 14:53
 */
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ICouponTemplateService couponTemplateService;
    @Autowired
    private IAdminService adminService;


    @PostMapping("/moveCouponTemplate")
    @Operation(summary = "数据迁移-多线程批量保存", description = "数据迁移-多线程批量保存")
    @Parameter(name = "limitTime", description = "分表迁移数据截止时间", required = true)
    @Parameter(name = "startId", description = "开始id", required = true)
    public Response moveCouponTemplate(@RequestParam("limitTime") String limitTime, @RequestParam(value = "startId", defaultValue = "1") Long startId) {
        adminService.moveCouponTemplate(limitTime, startId);
        return Response.success();
    }

    @PostMapping("/checkCouponTemplate")
    @Operation(summary = "对比优惠券模版数据", description = "对比优惠券模版数据")
    @Parameter(name = "status", description = "是否纠正 0-否， 1-是")
    public Response checkCouponTemplate(@RequestParam(value = "status", defaultValue = "0") Integer status) {
        adminService.checkCouponTemplate(status);
        return Response.success();
    }

    @PostMapping("/repairCouponTemplate")
    @Operation(summary = "纠正异常数据", description = "根据指定的券模版code纠正异常数据,支持批量")
    public Response repairCouponTemplate(@Valid @RequestBody List<String> couponTemplateCodes) {
        if (CollectionUtil.isNotEmpty(couponTemplateCodes)) {
            adminService.repairCouponTemplate(couponTemplateCodes);
        }
        return Response.success();
    }
}
