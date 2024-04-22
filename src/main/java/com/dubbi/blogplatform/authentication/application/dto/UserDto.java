package com.dubbi.blogplatform.authentication.application.dto;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String nickname;
    private Long age;
    private String city;
    private String email;
    private String role;
    private String socialType;

    public UserDto(User user){
        id = user.getId();
        nickname = user.getNickname();
        age = user.getAge();
        city = user.getCity();
        email = user.getEmail();
        role = user.getRoleKey();
        socialType = user.getSocialType().name();
    }
}
