package com.learn.douyin.follow.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.follow.mapper.FollowMapper;
import com.learn.douyin.follow.service.FollowService;
import com.learn.model.pojo.Follow;
import org.springframework.stereotype.Service;

@Service
public class FollowServiceImpl extends ServiceImpl<FollowMapper, Follow> implements FollowService {
}
