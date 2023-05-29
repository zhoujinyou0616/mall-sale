package com.eugene.common.config;

import com.eugene.response.Response;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ValidatedExceptionHandler {

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Response exceptionHandler2(BindException exception) {
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            return Response.error("500", result.getAllErrors().get(0).getDefaultMessage());
        }
        return Response.error("500", "请求参数错误！");
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response exceptionHandler2(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        if (result.hasErrors()) {
            return Response.error("500", result.getAllErrors().get(0).getDefaultMessage());
        }
        return Response.error("500", "请求参数错误！");
    }
}