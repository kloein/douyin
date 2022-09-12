package com.learn.douyin.like.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.model.pojo.Like;


/**
 * 这个接口的作用是帮助@Cacheable进行AOP，@Cacheable在内部调用时不会AOP，因此再写一个service帮助其AOP
 */
public interface LikeCntService extends IService<Like> {
    /**
     * 获取视频点赞量
     * @param vid
     * @return
     */
    public Integer getVideoLikeCnt(Long vid);
}
