package com.learn.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("user")
public class User {
    @TableId(value = "id",type = IdType.AUTO)
    private long id;

    @TableField("username")
    private String username;

    @TableField("password")
    private String password;

    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
