package com.learn.douyin.user.controller.api;

import com.learn.douyin.user.service.UserService;
import com.learn.model.user.UserMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/douyin/api/user")
public class UserApiController {
    @Autowired
    private UserService userService;

    @GetMapping("userList")
    public List<UserMsg> userList(@RequestParam("userIds")List<Long> userIds) {
        List<UserMsg> userMsgs=userService.getUserMsgByIds(userIds);
        return userMsgs;
    }
}