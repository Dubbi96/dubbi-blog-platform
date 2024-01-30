package com.dubbi.blogplatform.common.status;

import org.springframework.http.HttpStatus;

public enum HttpStatusEnum {
    OK(200, "OK",HttpStatus.OK),
    BAD_REQUEST(400, "BAD_REQUEST", HttpStatus.BAD_REQUEST),
    NOT_FOUND(404,"NOT_FOUND", HttpStatus.NOT_FOUND),
    UNAUTHORIZED(401,"Spring security unauthorized...", HttpStatus.UNAUTHORIZED),
    FORBIDDEN(403,"FORBIDDEN",HttpStatus.FORBIDDEN),
    INTERNAL_SERVER_ERROR(500,"INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);

    public int statusCode;
    String code;
    HttpStatus status;

    HttpStatusEnum(int statusCode, String code, HttpStatus status) {
        this.statusCode = statusCode;
        this.code = code;
        this.status = status;
    }
}
