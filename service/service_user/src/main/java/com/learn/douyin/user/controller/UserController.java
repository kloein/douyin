package com.learn.douyin.user.controller;

import com.learn.douyin.user.service.UserService;
import com.learn.model.response.LoginResponse;
import com.learn.model.response.RegisterResponse;
import com.learn.model.response.UserMsgResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "用户登录注册接口")
@RestController
@RequestMapping("/douyin/user")
public class UserController {
    @Autowired
    UserService userService;

    @ApiOperation("用户注册")
    @PostMapping("register")
    public RegisterResponse register(@RequestParam String username, @RequestParam String password){
        RegisterResponse response=userService.registerUser(username,password);
        return response;
    }
    @ApiOperation("用户登录")
    @PostMapping("login")
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        LoginResponse response=userService.loginUser(username,password);
        return response;
    }

    @ApiOperation("用户信息详情")
    @GetMapping("/")
    public UserMsgResponse userMsg(@RequestParam("user_id")Long userId,@RequestParam("token")String token){
        UserMsgResponse response=userService.getUserMsg(userId,token);
        return response;
    }

}
