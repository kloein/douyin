package com.learn.douyin.common.exception;

import com.learn.model.response.BaseResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public BaseResponse error(Exception e) {
        e.printStackTrace();
        return BaseResponse.fail();
    }

    @ExceptionHandler(DouyinException.class)
    @ResponseBody
    public BaseResponse error(DouyinException e) {
        e.printStackTrace();
        return BaseResponse.fail(e.getStatusMessage());
    }
}
