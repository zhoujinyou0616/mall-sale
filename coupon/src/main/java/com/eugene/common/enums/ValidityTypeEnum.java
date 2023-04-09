package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 有效期类型：0-起止日期 1-有效天数
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum ValidityTypeEnum {
    DEADLINE(0, "起止日期"),
    EFFECTIVE_DAY(1, "有效天数");

    private int code;
    private String name;

    private ValidityTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ValidityTypeEnum codeOf(int code) {
        for (ValidityTypeEnum status : ValidityTypeEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }


    public static boolean isDeadline(int code) {
        return ValidityTypeEnum.DEADLINE.getCode() == code;
    }

    public static boolean isEffectiveDay(int code) {
        return ValidityTypeEnum.EFFECTIVE_DAY.getCode() == code;
    }
}
