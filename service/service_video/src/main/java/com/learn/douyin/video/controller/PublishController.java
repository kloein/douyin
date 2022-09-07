package com.learn.douyin.video.controller;

import com.learn.douyin.video.service.VideoService;
import com.learn.model.response.PublishActionResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/douyin/publish")
public class PublishController {
    @Autowired
    private VideoService videoService;

    @ApiOperation("视频投稿")
    @PostMapping("action")
    public PublishActionResponse action(MultipartFile file,
                                        @RequestParam("token") String token,
                                        @RequestParam("title") String title
    ) {
        PublishActionResponse response=videoService.saveVideo(file,token,title);
        return response;
    }
}
