package com.learn.douyin.follow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.pojo.Follow;
import com.learn.model.user.UserMsg;

import java.util.List;
import java.util.Map;

public interface FollowService extends IService<Follow> {
    /**
     * 关注操作
     * @param userId
     * @param toUserId
     */
    void saveFollow(Long userId, Long toUserId);

    /**
     * 取消关注
     * @param userId
     * @param toUserId
     */
    void unfollow(Long userId, Long toUserId);

    /**
     * 查询用户关注的各个用户
     * @param userId
     * @param token
     * @return
     */
    List<UserMsg> getFollowList(Long userId, String token);

    /**
     * 查询用户粉丝
     * @param userId
     * @param token
     * @return
     */
    List<UserMsg> getFollowerList(Long userId, String token);

    /**
     * 查询用户间的关注信息
     * @return
     * @param uid
     * @param thisUid
     */
    Map<String, Object> getUserFollowMsg(Long uid, Long thisUid);
}
