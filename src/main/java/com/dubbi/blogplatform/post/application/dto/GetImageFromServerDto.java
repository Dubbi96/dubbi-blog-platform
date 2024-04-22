package com.dubbi.blogplatform.post.application.dto;

import lombok.Data;

@Data
public class GetImageFromServerDto {
    private String id;
    private String name;
    private String url;
    private String contentType;
}
