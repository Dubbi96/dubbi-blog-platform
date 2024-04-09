package com.dubbi.blogplatform.authentication.application.service;

import com.dubbi.blogplatform.authentication.application.dto.dto.GetUserDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.UserSignUpDto;
import com.dubbi.blogplatform.authentication.domain.entity.User;

public interface UserService {
    User signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException;

    /*void oauthEnroll(OAuthEnrollDto oAuthEnrollDto, String refreshToken)throws IllegalArgumentException;*/

    GetUserDto getUser(String accessToken);
}
