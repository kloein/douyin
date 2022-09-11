package com.learn.douyin.follow.controller;

import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.douyin.follow.service.FollowService;
import com.learn.model.enums.FollowActionTypeEnum;
import com.learn.model.response.RelationActionResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/douyin/relation")
public class FollowController {
    @Autowired
    private FollowService followService;

    @ApiOperation("关系操作")
    public RelationActionResponse action(
            @RequestParam("token")String token,
            @RequestParam("to_user_id")Long followId,
            @RequestParam("action_type")Integer actionType
    ) {
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        if (actionType.equals(FollowActionTypeEnum.FOLLOW_ACTION)) {

        }
        if (actionType.equals(FollowActionTypeEnum.UNFOLLOW_ACTION)) {

        }
        throw new DouyinException(ResultCodeEnum.FOLLOW_ACTION_TYPE_ERROR);
    }
}
