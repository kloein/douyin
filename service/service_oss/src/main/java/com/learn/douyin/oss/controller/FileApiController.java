package com.learn.douyin.oss.controller;

import com.learn.douyin.oss.service.FileService;
import com.learn.model.video.MultipartFileDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {
    @Autowired
    FileService fileService;

    /**
     * 上传视频到阿里云oss,返回文件url
     */
    @PostMapping("videoUpload")
    public String videoUpload(MultipartFile file) {
        String url=fileService.uploadVideo(file);
        return url;
    }

    /**
     * 上传图片到阿里云oss,返回文件url
     */
    @PostMapping("coverUpload")
    public String coverUpload(@RequestBody MultipartFileDto cover) {
        String url=fileService.uploadCover(cover);
        return url;
    }
}
