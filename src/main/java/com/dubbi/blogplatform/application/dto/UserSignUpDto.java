package com.dubbi.blogplatform.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserSignUpDto {
    private String email;
    private String password;
    private String nickname;
    private int age;
    private String city;
}
