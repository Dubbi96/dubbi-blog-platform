package com.dubbi.blogplatform.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UpdatePostDetailDto {
    private String title;
    private String content;
    private List<String> postImage;
}
