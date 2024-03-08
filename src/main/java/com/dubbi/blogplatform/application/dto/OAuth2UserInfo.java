package com.dubbi.blogplatform.application.dto;

import java.util.Map;

public abstract class OAuth2UserInfo {
    protected Map<String, Object> attributes;

    /**public -> protected로 변환 : abstract의 constructor는 protected로 만들어야 함
     * 이유 : 자식클래스가 아닌 다른 클래스에서 abstract객체에 대해서 재정의 하지 못하도록 막음*/
    protected OAuth2UserInfo(Map<String, Object> attributes){
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"

    public abstract String getNickname();

    public abstract String getImageUrl();
}
