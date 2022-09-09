package com.learn.douyin.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.response.PublishActionResponse;
import com.learn.model.pojo.Video;
import com.learn.model.response.PublishListResponse;
import com.learn.model.video.VideoMsg;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface VideoService extends IService<Video> {
    /**
     * 上传视频
     * @param file
     * @param token
     * @param title
     * @return
     */
    PublishActionResponse saveVideo(MultipartFile file, String token, String title);

    /**
     * 获取用户所以投稿过的视频
     * @param token
     * @param userId
     * @return
     */
    PublishListResponse getUserVideos(String token, Long userId);

    /**
     * 获取视频推送的feed流
     * @param latestTime
     * @param token
     * @return
     */
    Map<String, Object> feedLatest(Long latestTime, String token);

    /**
     * 根据id列表查询视频详细信息
     * @param videoIds
     * @return
     */
    List<VideoMsg> getVideoMsgsByIds(List<Long> videoIds);
}
