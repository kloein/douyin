package com.learn.douyin.user.controller;

import com.learn.douyin.user.service.UserService;
import com.learn.model.response.LoginResponse;
import com.learn.model.response.RegisterResponse;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户登录注册接口")
@RestController
@RequestMapping("/douyin/user")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("register")
    public RegisterResponse register(@RequestParam String username, @RequestParam String password){
        RegisterResponse response=userService.registerUser(username,password);
        return response;
    }

    @PostMapping("login")
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        LoginResponse response=userService.loginUser(username,password);
        return response;
    }
}
