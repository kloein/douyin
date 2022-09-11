package com.learn.douyin.follow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.follow.mapper.FollowMapper;
import com.learn.douyin.follow.service.FollowService;
import com.learn.douyin.user.client.UserFeignClient;
import com.learn.model.pojo.Follow;
import com.learn.model.user.UserMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
    @Autowired
    private UserFeignClient userFeignClient;
    @Override
    public void saveFollow(Long userId, Long toUserId) {
        //如果已经关注，那么不必关注
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", toUserId);
        wrapper.eq("follower_id", userId);
        Follow hasFollow = baseMapper.selectOne(wrapper);
        if (hasFollow != null) {
            return;
        }
        //关注
        Follow follow = new Follow();
        follow.setUid(toUserId);
        follow.setFollowerId(userId);
        baseMapper.insert(follow);
    }

    @Override
    public void unfollow(Long userId, Long toUserId) {
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", toUserId);
        wrapper.eq("follower_id", userId);
        baseMapper.delete(wrapper);
    }

    @Override
    public List<UserMsg> getFollowList(Long userId, String token) {
        //1、鉴定token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、查询用户关注的个体
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", userId);
        List<Follow> followList = baseMapper.selectList(wrapper);
        //3、远程调用user模块查询详细信息
        //将Follow列表转为id列表
        List<Long> ids=new ArrayList<>(followList.size());
        followList.stream().forEach(follow -> {
            ids.add(follow.getUid());
        });
        List<UserMsg> userMsgList = userFeignClient.userList(ids,token);
        return userMsgList;
    }

    @Override
    public List<UserMsg> getFollowerList(Long userId, String token) {
        //1、鉴定token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        //2、查询用户粉丝
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("uid", userId);
        List<Follow> followerList = baseMapper.selectList(wrapper);
        //3、远程调用user模块查询详细信息
        //将Follow列表转为id列表
        List<Long> ids=new ArrayList<>(followerList.size());
        followerList.stream().forEach(follower -> {
            ids.add(follower.getFollowerId());
        });
        List<UserMsg> userMsgList = userFeignClient.userList(ids,token);
        return userMsgList;
    }

    @Override
    public Map<String, Object> getUserFollowMsg(Long uid, Long thisUid) {
        Map<String,Object> map=new HashMap<>();
        //1、查询主体关注者数量
        QueryWrapper<Follow> wrapper = new QueryWrapper<>();
        wrapper.eq("follower_id", uid);
        Integer followCnt = baseMapper.selectCount(wrapper);
        map.put("followCnt", followCnt);
        //2、查询主体粉丝数量
        QueryWrapper<Follow> wrapperFans = new QueryWrapper<>();
        wrapperFans.eq("uid", uid);
        Integer fansCnt = baseMapper.selectCount(wrapperFans);
        map.put("followerCnt", fansCnt);
        //3、查询用户与主体之间关系
        QueryWrapper<Follow> relationWrapper = new QueryWrapper<>();
        relationWrapper.eq("uid", uid);
        relationWrapper.eq("follower_id", thisUid);
        Integer isFollow = baseMapper.selectCount(relationWrapper);
        map.put("isFollow", isFollow>=1?true:false);
        return map;
    }
}
