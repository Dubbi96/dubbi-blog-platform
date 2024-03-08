package com.dubbi.blogplatform.application.service;

import com.dubbi.blogplatform.application.dto.GetUserDto;
import com.dubbi.blogplatform.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.domain.entity.User;

public interface UserService {
    User signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException;

    /*void oauthEnroll(OAuthEnrollDto oAuthEnrollDto, String refreshToken)throws IllegalArgumentException;*/

    GetUserDto getUser(String accessToken);
}
