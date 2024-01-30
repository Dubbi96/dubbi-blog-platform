package com.dubbi.blogplatform.authentication.dto;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
@Slf4j
public enum OAuthCustomAttributes {
    GOOGLE("google", (attributes) -> {
        MemberProfile memberProfile = new MemberProfile();
        log.info("User Info : " + attributes);
        memberProfile.setName((String) attributes.get("name"));
        memberProfile.setEmail((String) attributes.get("email"));
        memberProfile.setPicture(((String) attributes.get("picture")));
        return memberProfile;
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        log.info("User Info : " + response);
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setName((String) response.get("name"));
        memberProfile.setEmail(((String) response.get("email")));
        memberProfile.setPicture(((String) response.get("profile_image")));
        return memberProfile;
    }),

    KAKAO("kakao", (attributes) -> {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");
        MemberProfile memberProfile = new MemberProfile();
        memberProfile.setName((String) kakaoProfile.get("nickname"));
        memberProfile.setPicture(((String) kakaoProfile.get("profile_image_url")));
        if(!(Boolean) kakaoAccount.get("email_needs_agreement") && (Boolean) kakaoAccount.get("has_email")){
            memberProfile.setEmail((String) kakaoAccount.get("email"));
        }
        return memberProfile;
    });

    private final String registrationId;
    private final Function<Map<String, Object>, MemberProfile> of;

    OAuthCustomAttributes(String registrationId, Function<Map<String, Object>, MemberProfile> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static MemberProfile extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }
}
