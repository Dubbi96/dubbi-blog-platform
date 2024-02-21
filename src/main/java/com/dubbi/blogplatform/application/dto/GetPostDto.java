package com.dubbi.blogplatform.application.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetPostDto {
    private String title;
    private String content;
    private Long creatorId;
    private int views;
    private List<String> postImages;
}
