package com.dubbi.blogplatform.authentication.service;

import com.dubbi.blogplatform.authentication.dto.MemberProfile;
import com.dubbi.blogplatform.authentication.dto.OAuthCustomAttributes;
import com.dubbi.blogplatform.authentication.entity.DubbiUser;
import com.dubbi.blogplatform.authentication.repository.DubbiUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DubbiOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final DubbiUserRepository dubbiUserRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); // OAuth 서비스(kakao, google, naver)에서 가져온 유저 정보를 담고있음

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //OAuth 로그인 시 키(PK)
        Map<String, Object> attributes = oAuth2User.getAttributes();

        MemberProfile memberProfile = OAuthCustomAttributes.extract(registrationId, attributes);
        log.info(attributes.toString());
        memberProfile.setProvider(registrationId);
        log.info(registrationId);

        DubbiUser dubbiUser = saveOrUpdate(memberProfile);

        Map<String, Object> customAttributes = customAttribute(attributes, userNameAttribute,memberProfile,registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                customAttributes,
                userNameAttribute);
    }

    private DubbiUser saveOrUpdate(MemberProfile memberProfile){
        DubbiUser member = dubbiUserRepository.findByUserNameAndProvider(memberProfile.getName(), memberProfile.getProvider())
                .map(m -> m.update(memberProfile.getName(), memberProfile.getPicture()))
                // OAuth 서비스 사이트에서 유저 정보 변경이 있을 수 있기 때문에 우리 DB에도 update
                .orElse(memberProfile.toMember());

        return dubbiUserRepository.save(member);
    }

    private Map<String, Object> customAttribute(Map<String, Object> attributes, String userNameAttributeName, MemberProfile memberProfile, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("provider", registrationId);
        customAttribute.put("email", memberProfile.getEmail());
        customAttribute.put("picture",memberProfile.getPicture());
        customAttribute.put("name", memberProfile.getName());
        return customAttribute;
    }
}
