package com.eugene.common.enums;

import com.eugene.response.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Errors implements ErrorCode {

    CREATE_ACTIVITY_CONFIG_ERROR("1000001", "创建活动设置不正确，请检查后重试！"),
    ACTIVITY_CAN_NOT_CREATE_ERROR("1000002", "创建拼团活动失败，请稍后重试！"),
    ACTIVITY_CAN_NOT_JOIN_ERROR("1000003", "创建拼团参加失败，请稍后重试！"),

    ;

    private final String code;
    private final String msg;


}
