package com.learn.douyin.common.exception;

import lombok.Data;
import lombok.Getter;

@Getter
public enum ResultCodeEnum {
    USERNAME_HAS_EXIST(201,"用户名已存在"),
    REGISTER_PARAM_ERROR(202,"用户名或密码错误"),
    USER_NOT_EXIST(203,"用户名不存在"),
    PASSWORD_ERROR(204,"登录密码错误"),
    ;

    private Integer code;
    private String statusMessage;

    private ResultCodeEnum(Integer code, String statusMessage) {
        this.code = code;
        this.statusMessage = statusMessage;
    }
}
