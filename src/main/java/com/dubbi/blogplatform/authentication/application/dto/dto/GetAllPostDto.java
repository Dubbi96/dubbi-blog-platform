package com.dubbi.blogplatform.authentication.application.dto.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class GetAllPostDto {
    private String title;
    private Long creatorId;
    private long views;
    private LocalDateTime createTs;
    private Long postCategoryId;
}
