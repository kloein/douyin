package com.learn.douyin.like;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients(basePackages = "com.learn.douyin")
@EnableDiscoveryClient
@ComponentScan("com.learn.douyin")
public class ServiceLikeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceLikeApplication.class, args);
    }
}
