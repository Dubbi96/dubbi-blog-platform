package com.dubbi.blogplatform.oneliner.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@Builder
@Getter
public class CreateOnelinerDto {
    private String content;
    private MultipartFile[] images;
}
