package com.learn.model.response;

public class RelationActionResponse extends BaseResponse{
    public static RelationActionResponse ok() {
        RelationActionResponse response = new RelationActionResponse();
        response.setStatusCode(0);
        return response;
    }
}
