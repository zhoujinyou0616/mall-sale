package com.eugene.mq.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户注册成功，发送消息通知
 * @Author eugene
 * @Data 2023/4/12 11:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserMessage implements Serializable {


    private static final long serialVersionUID = 3235532952423194570L;

    @Schema(description = "用户id", required = true)
    private Long id;

    @Schema(description = "手机号", required = true)
    private Long mobile;

    @Schema(description = "用户等级：0-游客，1-vip，2-店主", required = true)
    private Integer level;

    /**
     * 用户注册时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

}
