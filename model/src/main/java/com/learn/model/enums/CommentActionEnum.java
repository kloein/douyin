package com.learn.model.enums;

import lombok.Data;


public enum CommentActionEnum {
    COMMENT_ACTION(1),
    DELETE_COMMENT_ACTION(2)
    ;
    private Integer type;

    public Integer getType() {
        return type;
    }

    CommentActionEnum(Integer type) {
        this.type=type;
    }
}
