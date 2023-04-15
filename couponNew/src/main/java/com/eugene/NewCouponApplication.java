package com.eugene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.eugene"})
@EnableDiscoveryClient
@EnableFeignClients
public class NewCouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(NewCouponApplication.class, args);
    }
}
