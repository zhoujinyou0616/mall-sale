

package com.eugene.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @author eugene
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Response<T> implements DTO {

    // 错误码
    private String code = ResponseConstants.SUCCESS_CODE;

    // 错误信息文本
    private String msg = ResponseConstants.SUCCESS_MSG;

    // 消息主体
    private T data;

    protected Response() {
    }

    /**
     * 快速构建异常返回对象
     *
     * @param error
     * @param <T>   主体类型
     * @return 响应体
     */
    public static <T> Response<T> error(ErrorCode error) {
        return new Response<T>()
                .setCode(error.getCode())
                .setMsg(error.getMsg());
    }

    /**
     * 快速构建异常返回对象
     *
     * @param
     * @param <T> 主体类型
     * @return 响应体
     */
    public static <T> Response<T> error(String code, String msg) {
        return new Response<T>()
                .setCode(code)
                .setMsg(msg);
    }

    /**
     * 快速构建成功返回对象
     *
     * @param <T> 主体类型
     * @return 响应体
     */
    public static <T> Response<T> success() {
        return new Response<T>()
                .setCode(ResponseConstants.SUCCESS_CODE)
                .setMsg(ResponseConstants.SUCCESS_MSG);
    }

    /**
     * 快速构建带消息主体的成功返回对象
     *
     * @param data 消息主体
     * @param <T>  主体类型
     * @return 响应体
     */
    public static <T> Response<T> success(T data) {
        return (Response<T>) success().setData(data);
    }


}
