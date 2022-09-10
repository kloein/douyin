package com.learn.douyin.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.learn.douyin")
@ComponentScan("com.learn.douyin")
public class ServiceCommentApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCommentApplication.class, args);
    }
}
