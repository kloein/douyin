package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.user.UserMsg;
import lombok.Data;

import java.util.List;

@Data
public class RelationFollowListResponse extends BaseResponse{
    @JSONField(name = "user_list")
    private List<UserMsg> userMsgList;

    public static RelationFollowListResponse ok(List<UserMsg> userMsgList) {
        RelationFollowListResponse response = new RelationFollowListResponse();
        response.setStatusCode(0);
        response.setUserMsgList(userMsgList);
        return response;
    }
}
