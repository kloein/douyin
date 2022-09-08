package com.learn.douyin.user.client;

import com.learn.model.response.UserMsgResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-user")
@Repository
public interface UserFeignClient {

    @ApiOperation("用户信息详情")
    @GetMapping("/douyin/user/")
    public UserMsgResponse userMsg(@RequestParam("user_id")Long userId, @RequestParam("token")String token);
}
