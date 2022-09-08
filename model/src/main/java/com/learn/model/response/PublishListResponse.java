package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.video.VideoMsg;
import lombok.Data;

import java.util.List;

@Data
public class PublishListResponse extends BaseResponse{
    @JSONField(name = "video_list")
    private List<VideoMsg> videoMsgList;

    public static PublishListResponse ok(List<VideoMsg> videoMsgList) {
        PublishListResponse response = new PublishListResponse();
        response.setStatusCode(0);
        response.setVideoMsgList(videoMsgList);
        return response;
    }
}
