package com.dubbi.blogplatform.application.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class GetAllPostDto {
    private String title;
    private Long creatorId;
    private int views;
    private LocalDateTime createTs;
    private Long postCategoryId;
}
