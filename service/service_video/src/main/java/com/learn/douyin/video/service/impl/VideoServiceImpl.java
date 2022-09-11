package com.learn.douyin.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.like.client.LikeFeignClient;
import com.learn.douyin.oss.client.OssFeignClient;
import com.learn.douyin.user.client.UserFeignClient;
import com.learn.douyin.video.mapper.VideoMapper;
import com.learn.douyin.video.service.VideoService;

import com.learn.douyin.video.utils.VideoConstantPropertiesUtil;
import com.learn.model.pojo.User;
import com.learn.model.response.FeedResponse;
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

import java.util.*;
import java.util.concurrent.CountDownLatch;

@Service
public class VideoServiceImpl extends ServiceImpl<VideoMapper, Video> implements VideoService {

    @Autowired
    private OssFeignClient ossFeignClient;
    @Autowired
    private UserFeignClient userFeignClient;
    @Autowired
    private LikeFeignClient likeFeignClient;
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
        //4、封装视频
        List<VideoMsg> videoMsgList=this.packageVideos(videos,userMsg,userId);
        //5、构造返回值
        return PublishListResponse.ok(videoMsgList);
    }

    @Override
    public Map<String, Object> feedLatest(Long latestTime, String token) {
        //1、选取最新的视频
        QueryWrapper<Video> wrapper = new QueryWrapper<>();
        if (latestTime != null) {
            //参数检验。这是因为客户端有bug
            if (latestTime * 1000 >= new Date().getTime()) {
                latestTime=new Date((new Date().getTime())-7*24*60*60*1000L).getTime()/1000;
            }
            wrapper.ge("create_time",new Date(latestTime*1000));
        }
        wrapper.orderByDesc("create_time");
        //从配置类中获取拉取的视频数
        int feedVideoNum= VideoConstantPropertiesUtil.FEED_VIDEO_NUM;
        wrapper.last("limit "+feedVideoNum);
        List<Video> videos = baseMapper.selectList(wrapper);
        //2、找出它们发布的最早时间
        Date date=new Date();
        if (videos.size() > 0) {
            date=videos.get(videos.size()-1).getCreateTime();
        }
        //3、封装视频
        List<VideoMsg> feedList=new ArrayList<>(videos.size());
        Long uid=TokenUtil.getUserId(token);
        for (Video video : videos) {
            UserMsgResponse userMsgResponse = userFeignClient.userMsg(video.getUid(), token);
            UserMsg userMsg = userMsgResponse.getUserMsg();
            VideoMsg videoMsg = this.packageVideo(video, userMsg,uid);
            feedList.add(videoMsg);
        }
        //4、构造返回值
        Map<String,Object> map=new HashMap<>();
        map.put("feedList", feedList);
        map.put("nextTime", date);
        return map;
    }

    @Override
    public List<VideoMsg> getVideoMsgsByIds(List<Long> videoIds,String token) {
        //如果为空，sql语句会出错
        if (videoIds == null || videoIds.isEmpty()) {
            return new ArrayList<VideoMsg>();
        }
        List<Video> videos = baseMapper.selectBatchIds(videoIds);
        List<VideoMsg> videoMsgs=new ArrayList<>(videos.size());
        List<Long> videoUserIds=new ArrayList<>(videos.size());
        for (Video video : videos){
            videoUserIds.add(video.getUid());
        }
        //批量查询各视频用户信息
        List<UserMsg> userMsgs = userFeignClient.userList(videoUserIds,token);
        Long uid=TokenUtil.getUserId(token);
        //使用数组来储存多线程中有序的查询
        final VideoMsg[] videoMsgsArr=new VideoMsg[videos.size()];
        CountDownLatch countDownLatch = new CountDownLatch(videos.size());
        for (int i=0;i<videos.size();i++) {
            final int index=i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Video video=videos.get(index);
                        UserMsg userMsg = userMsgs.get(index);
                        VideoMsg videoMsg = packageVideo(video, userMsg,uid);
                        videoMsgsArr[index]=videoMsg;
                    }finally {
                        countDownLatch.countDown();
                    }
                }
            }).start();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //最后将数组转为集合
        for (int i=0;i< videoMsgsArr.length;i++){
            videoMsgs.add(videoMsgsArr[i]);
        }
        return videoMsgs;
    }

    private VideoMsg packageVideo(Video video, UserMsg userMsg,Long uid) {
        VideoMsg videoMsg = new VideoMsg();
        BeanUtils.copyProperties(video, videoMsg);
        videoMsg.setUserMsg(userMsg);
        //TODO
        videoMsg.setCommentCount(0L);

        Map<String, Object> map = likeFeignClient.getVideoFavoriteMsg(video.getId(), uid);
        Integer favoriteCount= (Integer) map.get("likeCnt");
        Boolean isLike= (Boolean) map.get("isLike");
        videoMsg.setFavoriteCount(favoriteCount.longValue());
        videoMsg.setIsFavorite(isLike);
        return videoMsg;
    }


    private List<VideoMsg> packageVideos(List<Video> videos, UserMsg userMsg,Long uid) {
        ArrayList<VideoMsg> videoMsgList = new ArrayList<>(videos.size());
        for (int i = 0; i < videos.size(); i++) {
            Video video=videos.get(i);
            VideoMsg videoMsg = this.packageVideo(video, userMsg,uid);
            videoMsgList.add(i, videoMsg);
        }
        return videoMsgList;
    }
}
