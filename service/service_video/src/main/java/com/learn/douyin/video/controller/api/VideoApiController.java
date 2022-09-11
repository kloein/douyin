package com.learn.douyin.video.controller.api;

import com.learn.douyin.video.service.VideoService;
import com.learn.model.video.VideoMsg;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("根据id列表批量查询视频详细信息")
    @GetMapping("videoList")
    public List<VideoMsg> videoList(@RequestParam("videoIds")List<Long> videoIds,@RequestParam("token")String token) {
        List<VideoMsg> videoMsgs=videoService.getVideoMsgsByIds(videoIds,token);
        return videoMsgs;
    }
}
