package com.learn.douyin.oss.controller;

import com.learn.douyin.common.utils.VideoHelper;
import com.learn.douyin.oss.service.FileService;
import com.learn.model.video.MultipartFileDto;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;

@RestController
@RequestMapping("/api/oss/file")
public class FileApiController {
    @Autowired
    FileService fileService;

    /**
     * 上传视频到阿里云oss,返回视频与封面url,0位置为视频url,1位置为封面url
     */
    @ApiOperation("视频与封面上传阿里云")
    @PostMapping("videoUpload")
    public String[] videoUpload(MultipartFile data) {
        String videoUrl=fileService.uploadVideo(data);
        MultipartFile cover=null;
        try {
            cover = VideoHelper.fetchFrame(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String coverUrl = fileService.uploadCover(cover);
        return new String[]{videoUrl,coverUrl};
    }
}
