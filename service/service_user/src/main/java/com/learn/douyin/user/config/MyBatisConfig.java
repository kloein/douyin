package com.learn.douyin.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.learn.douyin.user.mapper")
public class MyBatisConfig {
}
