package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.video.VideoMsg;
import lombok.Data;

import java.util.List;
@Data
public class FavoriteListResponse extends BaseResponse{
    @JSONField(name = "video_list")
    private List<VideoMsg> videoList;

    public static FavoriteListResponse ok(List<VideoMsg> videoList) {
        FavoriteListResponse response = new FavoriteListResponse();
        response.setStatusCode(0);
        response.setVideoList(videoList);
        return response;
    }
}
