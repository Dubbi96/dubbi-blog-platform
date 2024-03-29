package com.dubbi.blogplatform.application.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class UserSignUpDto {
    private String email;
    private String password;
    private String nickname;
    private long age;
    private String city;
}
