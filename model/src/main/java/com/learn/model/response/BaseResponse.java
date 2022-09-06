package com.learn.model.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;


@Data
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 1L;
    @JSONField(name="status_code")
    private int statusCode;

    @JSONField(name = "status_msg")
    private String statusMsg;

    public static BaseResponse fail(String statusMsg) {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(1);
        response.setStatusMsg(statusMsg);
        return response;
    }
    public static BaseResponse fail() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(1);
        return response;
    }
}
