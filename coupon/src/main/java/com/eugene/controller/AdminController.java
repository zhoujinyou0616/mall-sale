package com.eugene.controller;

import com.eugene.service.IAdminService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 管理员操作
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private IAdminService adminService;

    @PostMapping("/initCoupon")
    @Operation(summary = "初始化用户优惠券", description = "初始化用户信息")
    public void initUser() {
        // todo init

        System.out.println("init success");
    }


}
