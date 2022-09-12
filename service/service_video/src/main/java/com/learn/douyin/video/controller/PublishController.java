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
        new Thread(new Runnable() {
            @Override
            public void run() {
                //使用多线程异步上传视频
                videoService.saveVideo(data, token, title);
            }
        }).start();
        PublishActionResponse response=PublishActionResponse.ok();
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
