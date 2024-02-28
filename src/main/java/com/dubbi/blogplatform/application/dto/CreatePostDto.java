package com.dubbi.blogplatform.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreatePostDto {
    private String title;
    private String content;
    private MultipartFile[] postImage;
    private Long postCategoryId;
}
