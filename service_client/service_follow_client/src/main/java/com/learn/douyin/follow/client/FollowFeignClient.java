package com.learn.douyin.follow.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "service-follow")
@Repository
public interface FollowFeignClient {
    @GetMapping("/douyin/api/relation/userFollowMsg")
    public Map<String,Object> userFollowMsg(@RequestParam("uid") Long uid, @RequestParam("thisUid") Long thisUid);
}
