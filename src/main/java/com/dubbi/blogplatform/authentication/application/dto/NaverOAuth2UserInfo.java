package com.dubbi.blogplatform.authentication.application.dto;

import java.util.Map;

public class NaverOAuth2UserInfo extends com.dubbi.blogplatform.authentication.application.dto.OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }
    private Map<String,Object> getResponse(){
        return (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getId() {
        Map<String, Object> response = getResponse();

        if(response == null){
            return null;
        }

        return (String) response.get("id");
    }

    @Override
    public String getNickname() {
        Map<String, Object> response = getResponse();

        if(response == null){
            return null;
        }

        return (String) response.get("nickname");
    }

    @Override
    public String getImageUrl() {
        Map<String, Object> response = getResponse();

        if(response == null){
            return null;
        }

        return (String) response.get("profile_image");
    }
}
