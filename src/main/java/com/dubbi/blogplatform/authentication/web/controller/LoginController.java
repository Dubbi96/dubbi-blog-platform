package com.dubbi.blogplatform.authentication.web.controller;

import com.dubbi.blogplatform.authentication.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {
    @GetMapping("login")
    public String loginPage(){
        return "/login";
    }

    @GetMapping("posts")
    public String postPage(){
        log.error("to post page with");
        User loginInfo = (User)SecurityContextHolder.getContext().getAuthentication().getCredentials();
        log.info(loginInfo.getNickname());
        log.info(loginInfo.getEmail());
        return "redirect:/posts";}
}
