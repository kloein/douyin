package com.learn.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("t_like")
public class Like {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("uid")
    private Long uid;
    @TableField("vid")
    private Long vid;
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
