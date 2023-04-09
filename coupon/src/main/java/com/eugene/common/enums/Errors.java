package com.eugene.common.enums;

import com.eugene.response.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Errors implements ErrorCode {

    RECEIVE_COUPON_ERROR("1000000", "领券活动库存不足，请稍后再试！"),
    NOT_JOIN_RECEIVE_COUPON_ERROR("1000001", "当前领券活动您已达到最大参与次数，赶快去使用吧！"),

    NOT_COUPON_INFO_ERROR("1000002", "优惠券信息不存在或已使用，请稍后再试！");


    private final String code;
    private final String msg;

}
