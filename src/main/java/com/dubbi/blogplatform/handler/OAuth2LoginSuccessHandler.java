package com.dubbi.blogplatform.handler;

import com.dubbi.blogplatform.application.dto.CustomOAuth2User;
import com.dubbi.blogplatform.application.service.JwtService;
import com.dubbi.blogplatform.enumeratedclasses.Role;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtService jwtService;
    private static final String BASE_URL = "http://localhost:9002";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        log.info("OAuth2 Login 성공");
        try{
            CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

            //User의 Role이 GUEST일 경우 처음 요청한 회원이므로 회원가입 페이지로 리다이렉트
            if(oAuth2User.getRole() == Role.GUEST){
                String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
                response.addHeader(jwtService.getAccessHeader(),tokenToHeader(accessToken));
                response.sendRedirect(BASE_URL+"/signup.html"); //프론트 회원가입 추가 정보 입력 form으로 redirect

                jwtService.sendAccessAndRefreshToken(response, accessToken, null);
            }else {
                loginSuccess(response, oAuth2User);
            }
        }catch (Exception e){
            throw new IOException("cannot get principals from authentication " + e);
        }
    }

    private void loginSuccess(HttpServletResponse response, CustomOAuth2User oAuth2User) throws IOException {
        String accessToken = jwtService.createAccessToken(oAuth2User.getEmail());
        String refreshToken = jwtService.createRefreshToken();
        response.addHeader(jwtService.getAccessHeader(), tokenToHeader(accessToken));
        response.addHeader(jwtService.getRefreshHeader(), tokenToHeader(refreshToken));
        response.sendRedirect(BASE_URL+"/post.html");

        jwtService.sendAccessAndRefreshToken(response, accessToken, refreshToken);
        log.info("계정 이메일 : {} " , oAuth2User.getEmail());
        jwtService.updateRefreshToken(oAuth2User.getEmail(),refreshToken);
    }

    private String tokenToHeader(String token){
        return "Bearer " + token;
    }
}
