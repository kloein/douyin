package com.learn.model.response;

import lombok.Data;

@Data
public class PublishActionResponse extends BaseResponse{
    public static PublishActionResponse ok() {
        PublishActionResponse response = new PublishActionResponse();
        response.setStatusCode(0);
        return response;
    }
}
