package com.dubbi.blogplatform.common.status;

import lombok.Data;

@Data
public class HttpMessage {
    private HttpStatusEnum status;
    private String message;
    private Object data;

    public HttpMessage() {
        this.status = HttpStatusEnum.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }

    public HttpMessage(HttpStatusEnum status, String message, Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public HttpMessage(HttpStatusEnum status){
        this.status = status;
        this.message = status.code;
        this.data = null;
    }
}
