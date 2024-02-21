package com.dubbi.blogplatform.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreatePostDto {
    private String title;
    private String content;
    private List<String> postImage;
    private Long postCategoryId;
}
