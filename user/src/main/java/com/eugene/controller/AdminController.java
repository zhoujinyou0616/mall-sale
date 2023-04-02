package com.eugene.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.eugene.response.Response;
import com.eugene.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 管理员操作
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @PostMapping("/initUser")
    @Operation(summary = "初始化用户信息", description = "初始化用户信息")
    public void initUser() {
        adminService.initUser();
        // 1000万条数据插入耗时：6分钟
        System.out.println("init success");
    }

    @PostMapping("/moveShardingUsers")
    @Operation(summary = "分表数据迁移-1.0版本单线程", description = "1.0版本单线程")
    @Parameter(name = "limitTime", description = "分表迁移数据截止时间", required = true)
    @Parameter(name = "startUserId", description = "分表迁移开始UserId")
    public Response moveShardingUsers(@RequestParam("limitTime") String limitTime, @RequestParam(value = "startUserId", defaultValue = "1") Long startUserId) {
        long startTime = System.currentTimeMillis();
        adminService.moveShardingUsers(limitTime, startUserId);
        System.out.printf("迁移耗时：%d%d%n", (System.currentTimeMillis() - startTime) / 3600 + " 小时");
        return Response.success();
    }

    @PostMapping("/moveBatchShardingUsers")
    @Operation(summary = "分表数据迁移-2.0版本批量保存", description = "2.0版本批量保存")
    @Parameter(name = "limitTime", description = "分表迁移数据截止时间", required = true)
    @Parameter(name = "startUserId", description = "分表迁移开始UserId")
    public Response moveBatchShardingUsers(@RequestParam("limitTime") String limitTime, @RequestParam(value = "startUserId", defaultValue = "1") Long startUserId) {
        long startTime = System.currentTimeMillis();
        adminService.moveBatchShardingUsers(limitTime, startUserId);
        System.out.printf("迁移耗时：%d%d%n", System.currentTimeMillis() - startTime + " 小时");
        return Response.success();
    }

    @PostMapping("/threadsMoveBatchShardingUsers")
    @Operation(summary = "分表数据迁移-3.0版本多线程批量保存", description = "3.0版本多线程批量保存")
    @Parameter(name = "limitTime", description = "分表迁移数据截止时间", required = true)
    @Parameter(name = "startUserId", description = "分表迁移开始UserId")
    public Response threadsMoveBatchShardingUsers(@RequestParam("limitTime") String limitTime, @RequestParam(value = "startUserId", defaultValue = "1") Long startUserId) {
        adminService.threadMoveBatchShardingUsers(limitTime, startUserId);
        return Response.success();
    }

    @PostMapping("/checkUserData")
    @Operation(summary = "用户数据对比-多线程版本", description = "用户数据对比-多线程版本")
    @Parameter(name = "status", description = "是否纠正 0-否， 1-是")
    public Response checkUserData(@RequestParam(value = "status", defaultValue = "0") Integer status) {
        adminService.checkUserData(status);
        return Response.success();
    }

    @PostMapping("/repairUserData")
    @Operation(summary = "纠正异常用户数据", description = "根据指定的userIds纠正异常用户数据,支持批量")
    public Response repairUserData(@Valid @RequestBody List<Long> userIds) {
        if (CollectionUtil.isNotEmpty(userIds)) {
            adminService.repairUserData(userIds);
        }
        return Response.success();
    }


}
