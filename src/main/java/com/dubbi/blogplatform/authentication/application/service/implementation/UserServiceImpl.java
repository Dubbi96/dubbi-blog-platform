package com.dubbi.blogplatform.authentication.application.service.implementation;

import com.dubbi.blogplatform.authentication.application.dto.dto.GetUserDto;
import com.dubbi.blogplatform.authentication.application.dto.dto.UserSignUpDto;
import com.dubbi.blogplatform.authentication.application.service.JwtService;
import com.dubbi.blogplatform.authentication.application.service.UserService;
import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.authentication.domain.repository.UserRepository;
import com.dubbi.blogplatform.common.enumeratedclasses.Role;
import com.dubbi.blogplatform.common.enumeratedclasses.SocialType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    @Transactional
    public User signUp(UserSignUpDto userSignUpDto) throws IllegalArgumentException{
        if(userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("존재하는 회원입니다.");
        }
        if(userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()){
            throw new IllegalArgumentException("존재하는 닉네임입니다.");
        }
        User user = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .city(userSignUpDto.getCity())
                .role(Role.USER)
                .socialType(SocialType.NONE)
                .build();
        user.passwordEncode(passwordEncoder);
        return userRepository.save(user);
    }

    /*@Override
    @Transactional
    public void oauthEnroll(OAuthEnrollDto oAuthEnrollDto, String refreshToken)throws IllegalArgumentException{
        if(userRepository.findByEmail(oAuthEnrollDto.getEmail()).isPresent()){
            throw new IllegalArgumentException("존재하는 회원입니다.");
        }
        userRepository.findByRefreshToken(refreshToken)
                .ifPresent(user -> {user.oauthEnrollUpdate(oAuthEnrollDto);
                                    userRepository.saveAndFlush(user);});
    }*/

    @Override
    public GetUserDto getUser(String accessToken) {
        User user = userRepository.findByEmail(jwtService.extractEmail(accessToken).orElseThrow()).orElseThrow();
        return GetUserDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .picture(user.getProfilePicture().getUrl())
                .age(user.getAge())
                .city(user.getCity())
                .socialId(user.getSocialId()).build();
    }

}
