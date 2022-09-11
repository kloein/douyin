package com.learn.douyin.common.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    USERNAME_HAS_EXIST(201,"用户名已存在"),
    REGISTER_PARAM_ERROR(202,"用户名或密码错误"),
    USER_NOT_EXIST(203,"查无此用户"),
    PASSWORD_ERROR(204,"登录密码错误"),
    PARAM_MISSING(205,"参数不足"),
    TOKEN_ERROR(206,"token检验错误"),
    FAVORITE_ACTION_TYPE_ERROR(207,"点赞操作类型错误"),
    COMMENT_ACTION_TYPE_ERROR(208,"评论操作类型错误"),
    FOLLOW_ACTION_TYPE_ERROR(209,"关注操作类型错误"),
    ;

    private Integer code;
    private String statusMessage;

    private ResultCodeEnum(Integer code, String statusMessage) {
        this.code = code;
        this.statusMessage = statusMessage;
    }
}
