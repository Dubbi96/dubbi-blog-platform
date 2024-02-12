package com.dubbi.blogplatform.application.service;

import com.dubbi.blogplatform.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.domain.entity.User;

public interface UserService {
    public User signUp(UserSignUpDto userSignUpDto) throws Exception;
}
