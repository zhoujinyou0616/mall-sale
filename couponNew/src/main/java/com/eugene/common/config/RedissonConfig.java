package com.eugene.common.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @Description TODO
 * @Author eugene
 * @Data 2023/4/7 13:18
 */
@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedissonConfig {

    private String host = "127.0.0.1";

    private int port = 6379;

    @Bean
    public RedissonClient getRedissonClient() {
        Config config = new Config();
        // 设置编解码器
        config.setCodec(new JsonJacksonCodec());
        // 模式设置 单例
        config.useSingleServer();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        config.setCodec(new JsonJacksonCodec());
        return Redisson.create(config);
    }

}
