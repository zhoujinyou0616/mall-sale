package com.eugene.common.config;

import feign.Logger;
import feign.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/14 23:11
 */
@Configuration
public class FeignConfig {

    /**
     * feign日志级别
     * NONE（默认）：不记录任何日志，性能最佳，适用于生产环境；
     * BASIC：仅记录请求方法、URL、响应状态代码以及执行时间，适用于生产环境追踪问题；
     * HEADERS：在BASIC级别的基础上，记录请求和响应的header；
     * FULL：记录请求和响应的header、body和元数据，适用于开发测试定位问题。
     *
     * @return
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * feign 超时时间
     *
     * @return
     */
    @Bean
    public Request.Options options() {
        return new Request.Options(5000, 10000);
    }
}
