package com.eugene.common.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;

public class CustomFeignInterceptor implements RequestInterceptor {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void apply(RequestTemplate requestTemplate) {
        // requestTemplate.uri("/111"); 拦截所有路径，并修改为/{xx}
        requestTemplate.uri("/");
        logger.info("feign拦截器");
    }

    // 注入自定义feign拦截器
    @Bean
    public CustomFeignInterceptor customFeignInterceptor() {
        return new CustomFeignInterceptor();
    }

}
