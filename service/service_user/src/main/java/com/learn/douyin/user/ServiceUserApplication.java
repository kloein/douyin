package com.learn.douyin.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.learn.douyin")
@EnableFeignClients(basePackages = "com.learn.douyin")
@EnableDiscoveryClient
public class ServiceUserApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceUserApplication.class, args);
    }
}
