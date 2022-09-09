package com.learn.douyin.like.controller;

import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.like.service.LikeService;
import com.learn.model.response.FavoriteActionResponse;
import com.learn.model.response.FavoriteListResponse;
import com.learn.model.video.VideoMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/douyin/favorite")
public class LikeController {
    @Autowired
    private LikeService likeService;
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

    @GetMapping("list")
    public FavoriteListResponse list(@RequestParam("user_id")Long userId,
                                     @RequestParam("token") String token) {
        List<VideoMsg> videoList=likeService.listLiked(userId,token);
        return FavoriteListResponse.ok(videoList);
    }
}
