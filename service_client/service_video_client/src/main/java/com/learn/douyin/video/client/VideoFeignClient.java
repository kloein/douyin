package com.learn.douyin.video.client;

import com.learn.model.video.VideoMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "service-video")
@Repository
public interface VideoFeignClient {
    @GetMapping("/douyin/api/publish/videoList")
    public List<VideoMsg> videoList(@RequestParam("videoIds")List<Long> videoIds);
}
