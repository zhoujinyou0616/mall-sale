package com.eugene.common.config;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/6 22:14
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "configdata")
@RefreshScope
public class HotConfig {

    @Schema(description = "双写上线开关, S0-读旧写旧 S1-读旧双写 S2-读新双写 S3-读新写新")
    private String readAndWriteSwitch = "S0";
}
