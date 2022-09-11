package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.user.UserMsg;
import lombok.Data;

import java.util.List;

@Data
public class RelationFollowerListResponse extends BaseResponse{
    @JSONField(name = "user_list")
    private List<UserMsg> userMsgList;

    public static RelationFollowerListResponse ok(List<UserMsg> userMsgList) {
        RelationFollowerListResponse response = new RelationFollowerListResponse();
        response.setStatusCode(0);
        response.setUserMsgList(userMsgList);
        return response;
    }
}
