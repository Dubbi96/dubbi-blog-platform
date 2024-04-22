package com.dubbi.blogplatform.authentication.web.controller;

import com.dubbi.blogplatform.authentication.application.dto.GetUserDto;
import com.dubbi.blogplatform.authentication.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.authentication.application.service.UserService;
import com.dubbi.blogplatform.aspect.AccessTokenUser;
import com.dubbi.blogplatform.authentication.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody UserSignUpDto userSignUpDto){
        log.error(userSignUpDto.getNickname());
        User user = userService.signUp(userSignUpDto);
        return ResponseEntity.ok().body(user);
    }

    @GetMapping("/jwt-test")
    public String jwtTest(){
        return "jwtTest 요청 성공";
    }

    @GetMapping("/user-detail")
    public ResponseEntity<GetUserDto> getUser(@AccessTokenUser String accessToken){
        GetUserDto response = userService.getUser(accessToken);
        return ResponseEntity.ok().body(response);
    }

}
