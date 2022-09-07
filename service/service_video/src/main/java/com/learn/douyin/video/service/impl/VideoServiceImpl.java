package com.learn.douyin.video.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.oss.client.OssFeignClient;
import com.learn.douyin.video.mapper.VideoMapper;
import com.learn.douyin.video.service.VideoService;
import com.learn.douyin.video.utils.VideoHelper;
import com.learn.model.response.PublishActionResponse;
import com.learn.model.video.MultipartFileDto;
import com.learn.model.video.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private OssFeignClient ossFeignClient;
    @Override
    public PublishActionResponse saveVideo(MultipartFile file, String token, String title) {
        //1、验证token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、上传视频
        String videoUrl = ossFeignClient.videoUpload(file);
        //3、上传封面
        // 先截图
        MultipartFileDto cover = null;
        try {
            cover = VideoHelper.fetchFrame(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(cover);
        System.out.println(cover.getClass());
        String coverUrl=ossFeignClient.coverUpload(cover);
        //4、将数据存入数据库
        Video video = new Video();
        video.setUid(TokenUtil.getUserId(token));
        video.setPlayUrl(videoUrl);
        video.setCoverUrl(coverUrl);
        video.setTitle(title);
        baseMapper.insert(video);
        //5、构造返回值
        PublishActionResponse response=PublishActionResponse.ok();
        return response;
    }
}
