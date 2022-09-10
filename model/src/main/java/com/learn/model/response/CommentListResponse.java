package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.comment.CommentMsg;
import lombok.Data;

import java.util.List;

@Data
public class CommentListResponse extends BaseResponse{
    @JSONField(name = "comment_list")
    private List<CommentMsg> commentMsgList;

    public static CommentListResponse ok(List<CommentMsg> commentMsgList) {
        CommentListResponse response = new CommentListResponse();
        response.setStatusCode(0);
        response.setCommentMsgList(commentMsgList);
        return response;
    }
}
