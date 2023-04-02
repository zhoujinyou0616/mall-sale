package com.eugene.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger文档，只有在测试环境才会使用
 *
 * @author eugene
 */
@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi baseRestApi() {
        return GroupedOpenApi.builder()
                .group("接口文档")
                .packagesToScan("com.eugene").build();
    }


    @Bean
    public OpenAPI springShopOpenApi() {
        return new OpenAPI()
                .info(new Info().title("mall-user接口文档")
                        .description("mall-user接口文档，openapi3.0 接口，用于前端对接")
                        .version("v0.0.1"));
    }
}