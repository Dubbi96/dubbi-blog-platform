package com.dubbi.blogplatform.application.service;

import com.dubbi.blogplatform.application.dto.GetUserDto;
import com.dubbi.blogplatform.application.dto.OAuthEnrollDto;
import com.dubbi.blogplatform.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.aspect.AccessTokenUser;
import com.dubbi.blogplatform.domain.entity.User;

public interface UserService {
    User signUp(UserSignUpDto userSignUpDto) throws RuntimeException;

    void oauthEnroll(OAuthEnrollDto oAuthEnrollDto, String refreshToken) throws RuntimeException;

    GetUserDto getUser(@AccessTokenUser String accessToken);
}
