package com.learn.douyin.like.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.like.mapper.LikeMapper;
import com.learn.douyin.like.service.LikeCntService;
import com.learn.model.pojo.Like;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class LikeCntServiceImpl extends ServiceImpl<LikeMapper,Like>implements LikeCntService {
    @Cacheable(value = "likeCnt",keyGenerator = "likeKeyGenerator")
    @Override
    public Integer getVideoLikeCnt(Long vid) {
        QueryWrapper<Like> wrapper = new QueryWrapper<>();
        wrapper.eq("vid", vid);
        Integer likeCnt = baseMapper.selectCount(wrapper);
        return likeCnt;
    }
}
