package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 拼团状态：0:待支付;1:已支付;2:已成团;3:未成团,已退款;4:超时未成团,已退款
 * @Author eugene
 * @Data 2023/4/18 15:03
 */
@Getter
public enum PinTuanStatusEnum {
    NO_PAY(0, "待支付"),
    PAYED(1, "已支付"),
    SUCCESS(2, "已成团"),
    FAIL(3, "未成团,已退款"),
    TIME_OUT(4, "超时未成团,已退款");

    private int code;
    private String name;

    private PinTuanStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PinTuanStatusEnum codeOf(int code) {
        for (PinTuanStatusEnum status : PinTuanStatusEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

}
