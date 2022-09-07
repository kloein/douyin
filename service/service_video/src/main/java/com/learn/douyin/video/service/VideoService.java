package com.learn.douyin.video.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.response.PublishActionResponse;
import com.learn.model.video.Video;
import org.springframework.web.multipart.MultipartFile;

public interface VideoService extends IService<Video> {
    /**
     * 上传视频
     * @param file
     * @param token
     * @param title
     * @return
     */
    PublishActionResponse saveVideo(MultipartFile file, String token, String title);
}
