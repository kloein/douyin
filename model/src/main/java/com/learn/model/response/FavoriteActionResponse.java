package com.learn.model.response;

import lombok.Data;

@Data
public class FavoriteActionResponse extends BaseResponse{
    public static FavoriteActionResponse ok() {
        FavoriteActionResponse response = new FavoriteActionResponse();
        response.setStatusCode(0);
        return response;
    }
}
