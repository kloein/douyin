package com.learn.model.user;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class UserMsg {
    @JSONField(name = "id")
    private long id;
    @JSONField(name = "name")
    private String username;
    @JSONField(name = "follow_count")
    private long followCount;
    @JSONField(name = "follower_count")
    private long followerCount;
    @JSONField(name = "is_follow")
    private boolean isFollow;
}
