package com.eugene.common.enums;

import lombok.Getter;

/**
 * @Description 双写上线开关, S0-读旧写旧 S1-读旧双写 S2-读新双写 S3-读新写新
 * @Author eugene
 * @Data 2023/4/4 15:47
 */
@Getter
public enum ReadAndWriteSwitchEnum {
    S0("S0", "读旧写旧"),
    S1("S1", "读旧双写"),
    S2("S2", "读新双写"),
    S3("S3", "读新写新");

    private String code;
    private String name;

    private ReadAndWriteSwitchEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public static ReadAndWriteSwitchEnum codeOf(String code) {
        for (ReadAndWriteSwitchEnum status : ReadAndWriteSwitchEnum.values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

}
