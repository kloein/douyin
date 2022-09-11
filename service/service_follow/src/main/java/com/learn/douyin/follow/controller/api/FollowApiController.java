package com.learn.douyin.follow.controller.api;

import com.learn.douyin.follow.service.FollowService;
import com.learn.model.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/douyin/api/relation")
public class FollowApiController {
    @Autowired
    private FollowService followService;
    /**
     * 根据用户id，查询用户信息
     */
    @GetMapping("userFollowMsg")
    public Map<String,Object> userFollowMsg(@RequestParam("uid") Long uid,@RequestParam("thisUid") Long thisUid){
        Map<String,Object> map=followService.getUserFollowMsg(uid,thisUid);
        return map;
    }
}
