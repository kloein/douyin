package com.learn.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@TableName("t_comment")
@Data
public class Comment {
    @TableId(value = "id",type = IdType.AUTO )
    private Long id;
    @TableField("uid")
    private Long uid;
    @TableField("vid")
    private Long vid;
    @TableField("comment_text")
    private String commentText;
    @TableField("create_time")
    private Date createTime;
    @TableField("is_deleted")
    private Integer isDeleted;
}
