package com.learn.douyin.like.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(value = "service-like")
@Repository
public interface LikeFeignClient {
    @GetMapping("/douyin/api/like/getVideoFavoriteMsg")
    public Map<String, Object> getVideoFavoriteMsg(@RequestParam("vid")Long vid, @RequestParam("uid")Long uid);
}
