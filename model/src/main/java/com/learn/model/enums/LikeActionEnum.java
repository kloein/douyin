package com.learn.model.enums;

import lombok.Data;


public enum LikeActionEnum {
    LIKE_ACTION(1),
    UNLIKE_ACTION(2);

    private Integer type;

    LikeActionEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
