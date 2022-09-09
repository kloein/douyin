package com.learn.douyin.video.controller;

import com.learn.douyin.video.service.VideoService;
import com.learn.model.response.PublishActionResponse;
import com.learn.model.response.PublishListResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/douyin/publish")
public class PublishController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("视频投稿")
    @PostMapping("action")
    public PublishActionResponse action(MultipartFile data,
                                        @RequestParam("token") String token,
                                        @RequestParam("title") String title
    ) {
        //TODO 改为异步MQ的方式
        PublishActionResponse response=videoService.saveVideo(data,token,title);
        return response;
    }

    @ApiOperation("发布列表")
    @GetMapping("list")
    public PublishListResponse list(@RequestParam("token") String token,
                                    @RequestParam("user_id") Long userId
                                    ) {
        PublishListResponse response=videoService.getUserVideos(token,userId);
        return response;
    }
}
