package com.dubbi.blogplatform.application.dto;

import lombok.Data;

@Data
public class GetImageToServerResponseDto {
    private String id;
    private String name;
    private String url;
    private String contentType;
}
