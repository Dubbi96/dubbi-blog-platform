package com.dubbi.blogplatform.web;

import com.dubbi.blogplatform.application.dto.GetUserDto;
import com.dubbi.blogplatform.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.application.service.UserService;
import com.dubbi.blogplatform.aspect.AccessTokenUser;
import com.dubbi.blogplatform.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@RequestBody UserSignUpDto userSignUpDto){
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
