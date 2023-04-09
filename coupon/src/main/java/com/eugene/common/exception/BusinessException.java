package com.eugene.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description 业务异常类
 * @Author eugene
 * @Data 2023/4/8 20:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends Exception {

    private static final long serialVersionUID = -5454803250394717326L;


    private String errorCode;

    private String errorMessage;

}
