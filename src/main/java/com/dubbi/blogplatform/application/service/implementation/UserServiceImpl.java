package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.application.service.UserService;
import com.dubbi.blogplatform.domain.entity.User;
import com.dubbi.blogplatform.domain.repository.UserRepository;
import com.dubbi.blogplatform.enumeratedClasses.Role;
import com.dubbi.blogplatform.enumeratedClasses.SocialType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User signUp(UserSignUpDto userSignUpDto) throws Exception {
        if(userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()){
            throw new Exception("존재하는 회원입니다.");
        }
        if(userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()){
            throw new Exception("존재하는 닉네임입니다.");
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
}
