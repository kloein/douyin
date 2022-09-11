package com.learn.douyin.like.controller.api;

import com.learn.douyin.like.service.LikeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/douyin/api/like")
public class LikeApiController {

    @Autowired
    private LikeService likeService;

    @ApiOperation("获取视频点赞量以及用户是否点赞信息")
    @GetMapping("getVideoFavoriteMsg")
    public Map<String, Object> getVideoFavoriteMsg(@RequestParam("vid")Long vid,@RequestParam("uid")Long uid) {
        Map<String, Object> map=likeService.getVideoFavoriteMsg(vid,uid);
        return map;
    }
}
