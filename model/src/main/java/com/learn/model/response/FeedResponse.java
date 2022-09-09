package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import com.learn.model.video.VideoMsg;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FeedResponse extends BaseResponse{
    @JSONField(name = "video_list")
    private List<VideoMsg> videoList;
    @JSONField(name = "next_time")
    private Long nextTime;

    public static FeedResponse ok(List<VideoMsg> videoList, Date date) {
        FeedResponse response = new FeedResponse();
        response.setVideoList(videoList);
        response.setNextTime(date.getTime()/1000);//转化为秒
        return response;
    }
}
