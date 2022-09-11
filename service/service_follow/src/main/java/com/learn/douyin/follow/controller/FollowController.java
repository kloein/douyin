package com.learn.douyin.follow.controller;

import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.follow.service.FollowService;
import com.learn.model.enums.FollowActionTypeEnum;
import com.learn.model.response.RelationActionResponse;
import com.learn.model.response.RelationFollowListResponse;
import com.learn.model.response.RelationFollowerListResponse;
import com.learn.model.user.UserMsg;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/douyin/relation")
public class FollowController {
    @Autowired
    private FollowService followService;

    @ApiOperation("关系操作")
    @PostMapping("action")
    public RelationActionResponse action(
            @RequestParam("token") String token,
            @RequestParam("to_user_id") Long toUserId,
            @RequestParam("action_type") Integer actionType
    ) {
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        Long userId = TokenUtil.getUserId(token);
        if (actionType.equals(FollowActionTypeEnum.FOLLOW_ACTION.getType())) {
            followService.saveFollow(userId, toUserId);
            return RelationActionResponse.ok();
        }
        if (actionType.equals(FollowActionTypeEnum.UNFOLLOW_ACTION.getType())) {
            followService.unfollow(userId, toUserId);
            return RelationActionResponse.ok();
        }
        throw new DouyinException(ResultCodeEnum.FOLLOW_ACTION_TYPE_ERROR);
    }

    @ApiOperation("用户关注列表")
    @GetMapping("follow/list")
    public RelationFollowListResponse followList(
            @RequestParam("user_id")Long userId,
            @RequestParam("token")String token
    ){
        List<UserMsg> userMsgList=followService.getFollowList(userId,token);
        return RelationFollowListResponse.ok(userMsgList);
    }

    @ApiOperation("用户粉丝列表")
    @GetMapping("follower/list")
    public RelationFollowerListResponse followerList(
            @RequestParam("user_id")Long userId,
            @RequestParam("token")String token
    ){
        List<UserMsg> userMsgList=followService.getFollowerList(userId,token);
        return RelationFollowerListResponse.ok(userMsgList);
    }
}
