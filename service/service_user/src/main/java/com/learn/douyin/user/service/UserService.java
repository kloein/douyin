package com.learn.douyin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.response.LoginResponse;
import com.learn.model.response.RegisterResponse;
import com.learn.model.response.UserMsgResponse;
import com.learn.model.pojo.User;
import com.learn.model.user.UserMsg;

import java.util.List;

public interface UserService extends IService<User> {
    /**
     * 用户注册服务
     * @param username
     * @param password
     * @return
     */
    RegisterResponse registerUser(String username,String password);

    /**
     * 用户登录服务
     * @param username
     * @param password
     * @return
     */
    LoginResponse loginUser(String username, String password);

    /**
     * 获取用户信息详情
     * @param userId
     * @param token
     * @return
     */
    UserMsgResponse getUserMsg(Long userId, String token);

    /**
     * 根据用户id列表查询用户信息
     * @param userIds
     * @return
     */
    List<UserMsg> getUserMsgByIds(List<Long> userIds,String token);
}
