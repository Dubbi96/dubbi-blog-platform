package com.dubbi.blogplatform.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class UpdatePostDetailDto {
    private String title;
    private String content;
    private List<Long> deleteImageIds; // 삭제할 이미지의 ID 목록
    private List<MultipartFile> newPostImages; // 추가할 신규 이미지 파일 목록
}

