package com.learn.douyin.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.comment.CommentMsg;
import com.learn.model.pojo.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {
    /**
     * 评论操作
     * @param vid
     * @param commentContext
     * @param token
     * @return
     */
    CommentMsg comment(Long vid, String commentContext,String token);

    /**
     * 根据评论id进行删除，同时清理redis缓存
     * @param commentId
     * @return
     */
    void removeByIdWithCache(Long commentId);

    /**
     * 根据视频id获取视频评论
     * @param token
     * @param vid
     * @return
     */
    List<CommentMsg> getVideoCommentMsgs(String token, Long vid);
}
