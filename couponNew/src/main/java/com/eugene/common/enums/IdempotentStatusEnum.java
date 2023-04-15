package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 幂等状态枚举类
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum IdempotentStatusEnum {
    NO_START(0, "未开始"),
    IN_PROGRESS(1, "进行中"),
    FINISH(2, "处理完成");

    private int code;
    private String name;

    private IdempotentStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static IdempotentStatusEnum codeOf(int code) {
        for (IdempotentStatusEnum status : IdempotentStatusEnum.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        return null;
    }

    public static boolean isInProgress(String name) {
        return IdempotentStatusEnum.IN_PROGRESS.getName().equals(name);
    }

    public static boolean isFinish(String name) {
        return IdempotentStatusEnum.FINISH.getName().equals(name);
    }

}
