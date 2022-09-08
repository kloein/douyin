package com.learn.model.video;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.learn.model.user.UserMsg;
import lombok.Data;

@Data
public class VideoMsg {
    @JSONField(name = "id")
    private Long id;
    @JSONField(name = "author")
    private UserMsg userMsg;
    @JSONField(name = "play_url")
    private String playUrl;
    @JSONField(name = "cover_url")
    private String coverUrl;
    @JSONField(name = "favorite_count")
    private Long favoriteCount;
    @JSONField(name = "comment_count")
    private Long commentCount;
    @JSONField(name = "is_favorite")
    private Boolean isFavorite;
    @JSONField(name = "title")
    private String title;
}
