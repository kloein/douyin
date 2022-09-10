package com.learn.douyin.comment.cotroller;

import com.learn.douyin.comment.service.CommentService;
import com.learn.douyin.common.exception.DouyinException;
import com.learn.douyin.common.exception.ResultCodeEnum;
import com.learn.douyin.common.utils.TokenUtil;
import com.learn.model.comment.CommentMsg;
import com.learn.model.enums.CommentActionEnum;
import com.learn.model.response.BaseResponse;
import com.learn.model.response.CommentActionResponse;
import com.learn.model.response.CommentListResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/douyin/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation("评论操作")
    @PostMapping("action")
    public CommentActionResponse action(
                                    @RequestParam("token")String token,
                                    @RequestParam("video_id")Long vid,
                                    @RequestParam("action_type")Integer actionType,
                                    @RequestParam(value = "comment_text",required = false)String commentContext,
                                    @RequestParam(value = "comment_id",required = false)Long commentId
    ) {
        //校验token
        if (!TokenUtil.verify(token)) {
            throw new DouyinException(ResultCodeEnum.TOKEN_ERROR);
        }
        if (actionType.equals(CommentActionEnum.COMMENT_ACTION.getType())) {
            CommentMsg commentMsg=commentService.comment(vid,commentContext,token);
            return CommentActionResponse.ok(commentMsg);
        }
        if (actionType.equals(CommentActionEnum.DELETE_COMMENT_ACTION.getType())) {
            commentService.removeByIdWithCache(commentId);
            return CommentActionResponse.ok();
        }
        throw new DouyinException(ResultCodeEnum.COMMENT_ACTION_TYPE_ERROR);
    }

    @ApiOperation("视频评论列表")
    @GetMapping("list")
    public CommentListResponse list(
            @RequestParam("token")String token,
            @RequestParam("video_id")Long vid
    ) {
        List<CommentMsg> commentMsgList= commentService.getVideoCommentMsgs(token,vid);
        return CommentListResponse.ok(commentMsgList);
    }
}
