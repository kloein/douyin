package com.learn.douyin.video.controller;

import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.video.service.VideoService;
import com.learn.model.response.FeedResponse;
import com.learn.model.video.VideoMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class FeedController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("视频流接口")
    @GetMapping("/douyin/feed")
    public FeedResponse feed(@RequestParam(value = "latest_time",required = false)Long latestTime,
                             @RequestParam(value = "token",required = false) String token) {
        if (token == null) {//为游客用户创建临时token
            token= TokenUtil.createToken(-1L, "visitor");
        }
        Map<String,Object> resultMap=videoService.feedLatest(latestTime,token);
        List<VideoMsg> feedList= (List<VideoMsg>) resultMap.get("feedList");
        Date nextTime=(Date) resultMap.get("nextTime");
        return FeedResponse.ok(feedList,nextTime);
    }
}
