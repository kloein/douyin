package com.learn.douyin.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.like.mapper.LikeMapper;
import com.learn.douyin.like.service.LikeService;
import com.learn.douyin.video.client.VideoFeignClient;
import com.learn.model.enums.LikeActionEnum;
import com.learn.model.pojo.Like;
import com.learn.model.video.VideoMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Like> implements LikeService {
    @Autowired
    private VideoFeignClient videoFeignClient;
    @Override
    public void action(Long userId, String token, Long vid, Integer actionType) {
        //1、验证token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、点赞
        if (actionType.equals(LikeActionEnum.LIKE_ACTION.getType())) {
            QueryWrapper<Like> wrapper = new QueryWrapper<>();
            wrapper.eq("uid", userId);
            wrapper.eq("vid", vid);
            Like hasLike = baseMapper.selectOne(wrapper);
            //如果已经点赞了，那么不必再点
            if (hasLike == null) {
                Like like=new Like();
                like.setUid(userId);
                like.setVid(vid);
                baseMapper.insert(like);
            }
            return;
        }
        //3、取消赞
        if (actionType.equals(LikeActionEnum.UNLIKE_ACTION.getType())) {
            QueryWrapper<Like> wrapper = new QueryWrapper<>();
            wrapper.eq("uid", userId);
            wrapper.eq("vid", vid);
            Like hasLike = baseMapper.selectOne(wrapper);
            //如果没有点赞，那么才需要取消点赞
            if (hasLike!=null) {
                baseMapper.deleteById(hasLike.getId());
            }
            return;
        }
        //4、其他值
        throw new DouyinException(ResultCodeEnum.FAVORITE_ACTION_TYPE_ERROR);
    }

    @Override
    public List<VideoMsg> listLiked(Long userId, String token) {
        //1、验证token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、查询点赞过的视频
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", userId);
        List<Like> likeList = baseMapper.selectList(wrapper);
        //3、通过远程调用获取视频详细信息
        List<Long> likeIdsList=new ArrayList<>(likeList.size());
        for (Like like : likeList) {
            likeIdsList.add(like.getVid());
        }
        List<VideoMsg> videoMsgList = videoFeignClient.videoList(likeIdsList,token);
        return videoMsgList;
    }

    @Override
    public Map<String, Object> getVideoFavoriteMsg(Long vid, Long uid) {
        Map<String,Object> map=new HashMap<>();
        //1、获取视频点赞量
        Integer likeCnt = this.getVideoLikeCnt(vid);
        map.put("likeCnt", likeCnt);
        //2、获取用户是否已经点赞
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", uid);
        wrapper.eq("vid", vid);
        Integer isLike = baseMapper.selectCount(wrapper);
        map.put("isLike", isLike>=1?true:false);
        return map;
    }

    private Integer getVideoLikeCnt(Long vid) {//todo redis
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("vid", vid);
        Integer likeCnt = baseMapper.selectCount(wrapper);
        return likeCnt;
    }
}
