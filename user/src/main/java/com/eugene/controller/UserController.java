package com.eugene.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.controller.request.RegisterUserRequest;
import com.eugene.pojo.User;
import com.eugene.response.Response;
import com.eugene.service.IUserService;
import com.eugene.service.IUserShardingService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 用户相关
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserShardingService userShardingService;

    /**
     * 注册用户
     *
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "注册用户", description = "注册用户")
    public Response register(@RequestBody @Valid RegisterUserRequest request) {
        return Response.success(userShardingService.register(request));
    }

    @PostMapping("/getUser")
    @Operation(summary = "查询用户信息", description = "查询用户信息")
    public Response getUser(@RequestParam String mobile) {
        User user = userShardingService.getOne(new QueryWrapper<User>().eq("mobile", mobile).last("limit 1"));
        return Response.success(user);
    }


}
