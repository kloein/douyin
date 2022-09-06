package com.learn.douyin.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.response.LoginResponse;
import com.learn.model.response.RegisterResponse;
import com.learn.model.user.User;

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
}
