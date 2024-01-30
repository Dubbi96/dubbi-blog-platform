package com.dubbi.blogplatform.authentication.dto;

import com.dubbi.blogplatform.authentication.entity.DubbiUser;
import com.dubbi.blogplatform.authorization.role.Role;
import lombok.Data;

@Data
public class MemberProfile {
    private String name;
    private String email;
    private String provider;
    private String nickname;
    private String picture;

    public DubbiUser toMember(){
        return DubbiUser.builder()
                .userName(name)
                .email(email)
                .provider(provider)
                .nickname(nickname)
                .role(Role.MEMBER)
                .picture(picture)
                .build();
    }
}
