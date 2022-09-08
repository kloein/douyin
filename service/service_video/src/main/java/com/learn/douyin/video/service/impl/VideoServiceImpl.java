package com.learn.douyin.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.oss.client.OssFeignClient;
import com.learn.douyin.user.client.UserFeignClient;
import com.learn.douyin.video.mapper.VideoMapper;
import com.learn.douyin.video.service.VideoService;

import com.learn.model.response.PublishActionResponse;
import com.learn.model.pojo.Video;
import com.learn.model.response.PublishListResponse;
import com.learn.model.response.UserMsgResponse;
import com.learn.model.user.UserMsg;
import com.learn.model.video.VideoMsg;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private OssFeignClient ossFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Override
    public PublishActionResponse saveVideo(MultipartFile file, String token, String title) {
        //1、验证token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、上传视频,获取视频与封面地址
        String[] result= ossFeignClient.videoUpload(file);
        String videoUrl=result[0];
        String coverUrl=result[1];
        //3、将数据存入数据库
        Video video = new Video();
        video.setUid(TokenUtil.getUserId(token));
        video.setPlayUrl(videoUrl);
        video.setCoverUrl(coverUrl);
        video.setTitle(title);
        baseMapper.insert(video);
        //4、构造返回值
        PublishActionResponse response=PublishActionResponse.ok();
        return response;
    }

    @Override
    public PublishListResponse getUserVideos(String token, Long userId) {
        //1、验证token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、查询用户投稿视频
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", userId);
        List<Video> videos = baseMapper.selectList(wrapper);
        //3、查询用户信息
        UserMsg userMsg = userFeignClient.userMsg(userId, token).getUserMsg();
        System.out.println(userFeignClient.userMsg(userId, token));
        System.out.println(userMsg);
        //4、封装视频
        List<VideoMsg> videoMsgList=this.packageVideo(videos,userMsg);
        //5、构造返回值
        return PublishListResponse.ok(videoMsgList);
    }

    private List<VideoMsg> packageVideo(List<Video> videos, UserMsg userMsg) {
        ArrayList<VideoMsg> videoMsgList = new ArrayList<>(videos.size());
        for (int i = 0; i < videos.size(); i++) {
            VideoMsg videoMsg = new VideoMsg();
            Video video=videos.get(i);
            BeanUtils.copyProperties(video, videoMsg);
            videoMsg.setUserMsg(userMsg);
            //TODO
            videoMsg.setFavoriteCount(0L);
            videoMsg.setCommentCount(0L);
            videoMsg.setIsFavorite(false);
            videoMsgList.add(i, videoMsg);
        }
        return videoMsgList;
    }
}
