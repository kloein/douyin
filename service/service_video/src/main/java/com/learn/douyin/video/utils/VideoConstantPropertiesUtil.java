package com.learn.douyin.video.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class VideoConstantPropertiesUtil implements InitializingBean {
    @Value("${douyin.video.feed-video-num}")
    private int feedVideoNum;
    public static int FEED_VIDEO_NUM;
    @Override
    public void afterPropertiesSet() throws Exception {
        FEED_VIDEO_NUM=feedVideoNum;
    }
}
