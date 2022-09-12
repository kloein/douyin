package com.learn.douyin.like.controller;

import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.like.service.LikeService;
import com.learn.model.response.FavoriteActionResponse;
import com.learn.model.response.FavoriteListResponse;
import com.learn.model.video.VideoMsg;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/douyin/favorite")
public class LikeController {
    @Autowired
    private LikeService likeService;

    @ApiOperation("点赞操作")
    @PostMapping("action")
    public FavoriteActionResponse action(//@RequestParam("userId")Long userId,
                                         @RequestParam("token") String token,
                                         @RequestParam("video_id")Long vid,
                                         @RequestParam("action_type")Integer actionType
                                         ) {
        Long userId= TokenUtil.getUserId(token);
        likeService.action(userId,token,vid,actionType);
        return FavoriteActionResponse.ok();
    }

    @ApiOperation("点赞列表")
    @GetMapping("list")
    public FavoriteListResponse list(@RequestParam("user_id")Long userId,
                                     @RequestParam("token") String token) {
        List<VideoMsg> videoList=likeService.listLiked(userId,token);
        return FavoriteListResponse.ok(videoList);
    }
}
