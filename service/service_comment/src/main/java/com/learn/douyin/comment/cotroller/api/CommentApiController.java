package com.learn.douyin.comment.cotroller.api;

import com.learn.douyin.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/douyin/api/comment/getVideoCommentCnt")
    public Integer getVideoCommentCnt(@RequestParam("vid") Long vid) {
        Integer commentCnt=commentService.getVideoCommentCnt(vid);
        return commentCnt;
    }
}
