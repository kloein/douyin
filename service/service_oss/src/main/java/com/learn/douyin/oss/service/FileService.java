package com.learn.douyin.oss.service;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    /**
     * 上传视频的实现
     * @param video
     * @return
     */
    String uploadVideo(MultipartFile video);
    /**
     * 上传图片的实现
     * @param cover
     * @return
     */
    String uploadCover(MultipartFile cover);
}
