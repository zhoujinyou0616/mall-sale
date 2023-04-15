package com.eugene.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Description
 * @Author eugene
 */
@Data
public class RegisterUserRequest implements Serializable {

    @Schema(description = "用户昵称", required = true)
    @NotBlank
    private String name;

    @Schema(description = "手机号", required = true)
    @NotNull
    private Long mobile;


}
