package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 可用状态枚举类
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum StatusEnum {
    NOT_AVAILABLE(0, "不可用"),
    AVAILABLE(1, "可用");

    private int code;
    private String name;

    private StatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static StatusEnum codeOf(int code) {
        for (StatusEnum status : StatusEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

}
