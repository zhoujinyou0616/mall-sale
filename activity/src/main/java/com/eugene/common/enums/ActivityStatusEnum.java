package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 活动状态枚举类
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum ActivityStatusEnum {
    NO_START(0, "未开始", "no_start"),
    IN_PROGRESS(1, "进行中", "in_progress"),
    FINISH(2, "已结束", "finish");

    private int code;
    private String name;
    private String tag;

    private ActivityStatusEnum(int code, String name, String tag) {
        this.code = code;
        this.name = name;
        this.tag = tag;
    }

    public static ActivityStatusEnum codeOf(int code) {
        for (ActivityStatusEnum status : ActivityStatusEnum.values()) {
            if (status.code == code) {
                return status;
            }
        }
        return null;
    }

}
