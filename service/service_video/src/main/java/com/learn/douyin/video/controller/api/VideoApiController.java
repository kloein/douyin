package com.learn.douyin.video.controller.api;

import com.learn.douyin.video.service.VideoService;
import com.learn.model.video.VideoMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/douyin/api/publish")
public class VideoApiController {
    @Autowired
    private VideoService videoService;
    /**
     * 根据id列表查询视频详细信息
     * @param videoIds
     * @return
     */
    @GetMapping("videoList")
    public List<VideoMsg> videoList(@RequestParam("videoIds")List<Long> videoIds) {
        List<VideoMsg> videoMsgs=videoService.getVideoMsgsByIds(videoIds);
        return videoMsgs;
    }
}
