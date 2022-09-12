package com.learn.douyin.comment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.douyin.comment.mapper.CommentMapper;
import com.learn.douyin.comment.service.CommentService;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.user.client.UserFeignClient;
import com.learn.model.comment.CommentMsg;
import com.learn.model.pojo.Comment;
import com.learn.model.user.UserMsg;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {
    @Autowired
    private UserFeignClient userFeignClient;

    @CacheEvict(value = "commentCnt",keyGenerator = "keyGenerator")
    @Override
    public CommentMsg comment(Long vid, String commentContext,String token) {
        //插入
        Comment comment = new Comment();
        comment.setUid(TokenUtil.getUserId(token));
        comment.setVid(vid);
        comment.setCommentText(commentContext);
        comment.setCreateTime(new Date());
        baseMapper.insert(comment);
        //构造返回值
        CommentMsg commentMsg=this.packageComment(comment,token);
        return commentMsg;
    }

    @CacheEvict(value = "commentCnt",keyGenerator = "keyGenerator")
    @Override
    public void removeByIdWithCache(Long commentId) {
         baseMapper.deleteById(commentId);
    }

    @Override
    public List<CommentMsg> getVideoCommentMsgs(String token, Long vid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("vid", vid);
        List<Comment> commentList = baseMapper.selectList(wrapper);
        List<CommentMsg> commentMsgList=new ArrayList<>(commentList.size());
        commentList.stream().forEach(comment -> {
            commentMsgList.add(this.packageComment(comment,token));
        });
        return commentMsgList;
    }

    @Cacheable(value = "commentCnt",keyGenerator = "keyGenerator")
    @Override
    public Integer getVideoCommentCnt(Long vid) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("vid", vid);
        Integer commentCnt = baseMapper.selectCount(wrapper);
        return commentCnt;
    }

    public CommentMsg packageComment(Comment comment,String token) {
        CommentMsg commentMsg=new CommentMsg();
        commentMsg.setId(comment.getId());
        commentMsg.setCreateDate(new DateTime(comment.getCreateTime()).toString("MM-dd"));
        commentMsg.setContext(comment.getCommentText());
        //查询用户信息
        Long userId= TokenUtil.getUserId(token);
        UserMsg userMsg = userFeignClient.userMsg(userId, token).getUserMsg();
        commentMsg.setUserMsg(userMsg);
        return commentMsg;
    }
}
