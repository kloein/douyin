package com.learn.model.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("video")
public class Video {
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    @TableField("uid")
    private Long uid;
    @TableField("play_url")
    private String playUrl;
    @TableField("cover_url")
    private String coverUrl;
    @TableField("title")
    private String title;
    @TableLogic
    @TableField("is_deleted")
    private Integer isDeleted;
}
