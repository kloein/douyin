package com.learn.douyin.oss.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;


@FeignClient(value = "service-oss")
@Repository
public interface OssFeignClient {
    /**
     * 上传视频到阿里云oss,返回视频与封面url,0位置为视频url,1位置为封面url
     */
    @PostMapping(value = "/api/oss/file/videoUpload",consumes = "multipart/form-data")
    public String[] videoUpload(MultipartFile video);

}
