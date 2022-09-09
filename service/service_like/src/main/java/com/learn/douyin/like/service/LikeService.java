package com.learn.douyin.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.pojo.Like;
import com.learn.model.video.VideoMsg;

import java.util.List;

public interface LikeService extends IService<Like> {
    /**
     * 点赞或取消赞操作
     * @param userId
     * @param token
     * @param vid
     * @param actionType
     */
    void action(Long userId, String token, Long vid, Integer actionType);

    /**
     * 根据用户id查询其点赞过的视频
     * @param userId
     * @param token
     * @return
     */
    List<VideoMsg> listLiked(Long userId, String token);
}
