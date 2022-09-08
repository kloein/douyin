package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.user.UserMsg;
import lombok.Data;

@Data
public class UserMsgResponse extends BaseResponse{
    @JSONField(name = "user")
    private UserMsg userMsg;

    public static UserMsgResponse ok(UserMsg userMsg) {
        UserMsgResponse response = new UserMsgResponse();
        response.setStatusCode(0);
        response.setUserMsg(userMsg);
        return response;
    }
}
