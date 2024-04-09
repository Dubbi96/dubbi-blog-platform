package com.dubbi.blogplatform.authentication.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface JwtService {
    /**인터페이스를 사용한면서 구현체의 get method도 사용하고 싶어 추가*/
    String getSecretKey();
    Long getAccessTokenExpirationPeriod();
    Long getRefreshTokenExpirationPeriod();
    String getAccessHeader();
    String getRefreshHeader();

    /** AccessToken 생성 메소드*/
    String createAccessToken(String email);

    /** RefreshToken 생성
     * RefreshToken은 Claim에 email도 넣지 않으므로 withClaim() X*/
    String createRefreshToken();

    /** AccessToken 헤더에 실어서 보내기*/
    void sendAccessToken(HttpServletResponse response, String accessToken);

    /** AccessToken + RefreshToken 헤더에 실어서 보내기*/
    void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken);

    /**헤더에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)*/
    Optional<String> extractRefreshToken(HttpServletRequest request);

    /**헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX에서 Bearer를 제외하고 순수 토큰만 가져오기 위해서
     * 헤더를 가져온 후 "Bearer"를 삭제(""로 replace)*/
    Optional<String> extractAccessToken(HttpServletRequest request);

    /** AccessToken에서 Email 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 이메일 추출
     * 유효하지 않다면 빈 Optional 객체 반환*/
    Optional<String> extractEmail(String accessToken);

    /** AccessToken에서 Nickname 추출
     * 추출 전에 JWT.require()로 검증기 생성
     * verify로 AceessToken 검증 후
     * 유효하다면 getClaim()으로 닉네임 추출
     * 유효하지 않다면 빈 Optional 객체 반환*/
    Optional<String> extractNickname(String nickname);

    /** RefreshToken DB 저장(업데이트)*/
    void updateRefreshToken(String email, String refreshToken);

    /**token 인증 함수*/
    boolean isTokenValid(String token);
}
