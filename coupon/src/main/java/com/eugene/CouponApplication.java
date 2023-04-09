package com.eugene;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.eugene"})
@EnableDiscoveryClient
public class CouponApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponApplication.class, args);
    }
}
