package com.eugene.common.config;

import com.eugene.common.exception.BusinessException;
import com.eugene.response.Response;
import com.eugene.response.ResponseConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/8 20:31
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler
    public Response defaultErrorHandler(Exception e) {
        log.error("服务器异常", e);
        e.printStackTrace();
        if (e instanceof BusinessException) {
            BusinessException businessException = (BusinessException) e;
            return Response.error(businessException.getErrorCode(), businessException.getErrorMessage());
        } else {
            return Response.error(ResponseConstants.ERROR_CODE, e.getMessage());
        }
    }

}
