package com.dubbi.blogplatform.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class GetUserDto {
    private String email;
    private String nickname;
    private String picture;
    private long age;
    private String city;
    private String socialId;
}
