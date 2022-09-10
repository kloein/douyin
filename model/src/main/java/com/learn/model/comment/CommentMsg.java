package com.learn.model.comment;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.user.UserMsg;
import lombok.Data;

@Data
public class CommentMsg {
    @JSONField(name = "id")
    private Long id;
    @JSONField(name = "user")
    private UserMsg userMsg;
    @JSONField(name = "content")
    private String context;
    @JSONField(name = "create_date")
    private String createDate;
}
