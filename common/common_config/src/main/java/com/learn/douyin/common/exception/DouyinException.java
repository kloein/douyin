package com.learn.douyin.common.exception;

import lombok.Data;

@Data
public class DouyinException extends RuntimeException{
    private Integer code;

    private String statusMessage;

    public DouyinException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public DouyinException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getStatusMessage());
        this.statusMessage=resultCodeEnum.getStatusMessage();
        this.code=resultCodeEnum.getCode();
    }

    @Override
    public String toString() {
        return "DouyinException{" +
                "code=" + code +
                ", statusMessage=" + this.getStatusMessage() +
                '}';
    }
}
