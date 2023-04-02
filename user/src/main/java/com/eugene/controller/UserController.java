package com.eugene.controller;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.eugene.pojo.User;
import com.eugene.response.Response;
import com.eugene.service.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 用户相关
 */
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 注册用户
     *
     * @return
     */
    @PostMapping("/register")
    @Operation(summary = "注册用户", description = "注册用户")
    public Response register() {
        Snowflake snowflake = new Snowflake();
        User user = new User();
        user.setId(snowflake.nextId());
        user.setName("金金金金金金有");
        user.setMobile(13010000001L);
        user.setLevel(1);
        user.setTags("10,12");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return Response.success(userService.save(user));
    }

    @PostMapping("/getUser")
    @Operation(summary = "查询用户信息", description = "查询用户信息")
    public Response getUser(@RequestParam String mobile) {
        User user = userService.getOne(new QueryWrapper<User>().eq("mobile", mobile).last("limit 1"));
        return Response.success(user);
    }


}
