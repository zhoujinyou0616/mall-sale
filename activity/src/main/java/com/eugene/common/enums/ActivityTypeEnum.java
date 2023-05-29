package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 活动类型：0-秒杀 1-拼团 2-砍价
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum ActivityTypeEnum {
    SECOND_KILL(0, "秒杀"),
    PIN_TUAN(1, "拼团"),
    BARGAIN(2, "砍价");

    private int code;
    private String name;

    private ActivityTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ActivityTypeEnum codeOf(int code) {
        for (ActivityTypeEnum status : ActivityTypeEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

}
