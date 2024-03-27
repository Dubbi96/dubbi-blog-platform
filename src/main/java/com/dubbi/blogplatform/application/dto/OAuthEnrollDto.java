package com.dubbi.blogplatform.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class OAuthEnrollDto {
    private String email;
    private long age;
    private String city;
}
