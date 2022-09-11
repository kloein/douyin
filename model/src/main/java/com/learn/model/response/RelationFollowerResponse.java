package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.user.UserMsg;
import lombok.Data;

import java.util.List;

@Data
public class RelationFollowerResponse extends BaseResponse{
    @JSONField(name = "user_list")
    private List<UserMsg> userMsgList;

    public static RelationFollowerResponse ok(List<UserMsg> userMsgList) {
        RelationFollowerResponse response = new RelationFollowerResponse();
        response.setStatusCode(0);
        response.setUserMsgList(userMsgList);
        return response;
    }
}
