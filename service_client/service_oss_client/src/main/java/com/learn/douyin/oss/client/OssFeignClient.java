package com.learn.douyin.oss.client;

import com.learn.model.video.MultipartFileDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@FeignClient(value = "service-oss")
@Repository
public interface OssFeignClient {
    /**
     * 上传视频到阿里云oss,返回文件url
     */
    @PostMapping(value = "/api/oss/file/videoUpload",consumes = "multipart/form-data")
    public String videoUpload(MultipartFile video);

    /**
     * 上传图片到阿里云oss,返回文件url
     */
    @PostMapping(value = "/api/oss/file/coverUpload",consumes = "multipart/form-data")
    public String coverUpload(@RequestBody MultipartFileDto video);
}
