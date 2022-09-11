package com.learn.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_follow")
public class Follow {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("uid")
    private Long uid;
    @TableField("follower_id")
    private Long followerId;
    @TableField("create_time")
    private Date createTime;
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
