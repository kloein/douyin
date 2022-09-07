package com.learn.douyin.video.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.learn.douyin.video.mapper")
public class MyBatisConfig {
}
