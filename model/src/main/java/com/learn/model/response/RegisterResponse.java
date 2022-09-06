package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class RegisterResponse extends BaseResponse{
    @JSONField(name = "user_id")
    private long userId;
    @JSONField(name = "token")
    private String token;

    public static RegisterResponse ok(long userId,String token) {
        RegisterResponse response = new RegisterResponse();
        response.setStatusCode(0);
        response.setUserId(userId);
        response.setToken(token);
        return response;
    }

    public static RegisterResponse fail(String statusMsg) {
        RegisterResponse response = new RegisterResponse();
        response.setStatusCode(1);
        response.setStatusMsg(statusMsg);
        return response;
    }
}
