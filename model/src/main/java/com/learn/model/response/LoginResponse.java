package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class LoginResponse extends BaseResponse{
    @JSONField(name = "user_id")
    private long userId;
    @JSONField(name = "token")
    private String token;
    public static LoginResponse ok(long userId,String token) {
        LoginResponse response = new LoginResponse();
        response.setStatusCode(0);
        response.setUserId(userId);
        response.setToken(token);
        return response;
    }
}
