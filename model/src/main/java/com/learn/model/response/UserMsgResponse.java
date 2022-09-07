package com.learn.model.response;

import com.learn.model.user.UserMsg;
import lombok.Data;

@Data
public class UserMsgResponse extends BaseResponse{
    private UserMsg userMsg;

    public static UserMsgResponse ok(UserMsg userMsg) {
        UserMsgResponse response = new UserMsgResponse();
        response.setStatusCode(0);
        response.setUserMsg(userMsg);
        return response;
    }
}
