package com.learn.model.enums;

public enum FollowActionTypeEnum {
    FOLLOW_ACTION(1),
    UNFOLLOW_ACTION(2);

    private Integer type;

    public Integer getType() {
        return type;
    }

    FollowActionTypeEnum(Integer type) {
        this.type = type;
    }
}
