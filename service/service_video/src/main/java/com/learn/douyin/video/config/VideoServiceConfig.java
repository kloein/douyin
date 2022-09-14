package com.learn.douyin.video.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

@Configuration
public class VideoServiceConfig {
    @Bean("feedExecutor")
    public Executor feedExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100, 600, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100), new ThreadPoolExecutor.DiscardOldestPolicy());
        return threadPoolExecutor;
    }
}
