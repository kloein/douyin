package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.comment.CommentMsg;
import lombok.Data;

@Data
public class CommentActionResponse extends BaseResponse{
    @JSONField(name = "comment")
    private CommentMsg commentMsg;

    public static CommentActionResponse ok(CommentMsg commentMsg) {
        CommentActionResponse response = new CommentActionResponse();
        response.setStatusCode(0);
        response.setCommentMsg(commentMsg);
        return response;
    }
    public static CommentActionResponse ok() {
        CommentActionResponse response = new CommentActionResponse();
        response.setStatusCode(0);
        return response;
    }
}
