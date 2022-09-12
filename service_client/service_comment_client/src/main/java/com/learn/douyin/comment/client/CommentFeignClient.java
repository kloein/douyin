package com.learn.douyin.comment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
@FeignClient(value = "service-comment")
public interface CommentFeignClient {

    @GetMapping("/douyin/api/comment/getVideoCommentCnt")
    public Integer getVideoCommentCnt(@RequestParam("vid") Long vid);
}
