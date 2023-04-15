package com.eugene.pojo;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.eugene.controller.request.RegisterUserRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户表
 * @Author eugene
 */
@Data
@TableName("t_user")
@EqualsAndHashCode
public class User implements Serializable {
    private static final long serialVersionUID = -8891409800100222999L;
    @TableId(type = IdType.INPUT)
    @Schema(description = "用户id", required = true)
    private Long id;

    @Schema(description = "用户昵称", required = true)
    private String name;


    @Schema(description = "手机号", required = true)
    private Long mobile;


    @Schema(description = "用户等级：0-游客，1-vip，2-店主", required = true)
    private Integer level;

    @Schema(description = "用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主")
    private String tags;

    /**
     * 用户注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 用户更新时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    public static User converUser(RegisterUserRequest request) {
        Snowflake snowflake = new Snowflake();
        User user = new User();
        user.setId(snowflake.nextId());
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        // 用户等级：0-游客，1-vip，2-店主
        user.setLevel(1);
        // 用户标签 10-新VIP，11-老VIP，12-未自购VIP、13-已自购VIP；20-新店主，21-老店主2，2-未自购店主，23-已自购店主，24-未开单店主，25-已开单店主
        user.setTags("10,12");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        return user;
    }
}
