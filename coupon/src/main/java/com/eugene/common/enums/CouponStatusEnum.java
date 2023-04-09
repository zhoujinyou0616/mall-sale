package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 优惠券状态枚举类
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum CouponStatusEnum {
    NOT_AVAILABLE(0, "不可用"),
    AVAILABLE(1, "可用"),
    USED(2, "已使用"),
    EXPIRED(3, "已过期");

    private int code;
    private String name;

    private CouponStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CouponStatusEnum codeOf(int code) {
        for (CouponStatusEnum status : CouponStatusEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

}
