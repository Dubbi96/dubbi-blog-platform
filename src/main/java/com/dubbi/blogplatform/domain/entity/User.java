package com.dubbi.blogplatform.domain.entity;

import com.dubbi.blogplatform.enumeratedClasses.Role;
import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import com.dubbi.blogplatform.enumeratedClasses.SocialType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
@AllArgsConstructor
public class User extends BaseEntity {

    @Column(name = "E_MAIL")
    private String email;

    private String password;

    @Column(name = "NICKNAME")
    private String nickname;

    @Column(name = "PROFILE_PICTURE")
    private String picture;

    private int age;

    private String city;

    @Column(name = "SOCIAL_ID",unique = true)
    private String socialId;

    @Column(name = "SOCIAL_TYPE", nullable = false)
    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    private String refreshToken;

    public String getRoleKey(){
        return this.role.getKey();
    }

    public void authorizeUser(){
        this.role = Role.USER;
    }

    // 비밀번호 암호화 메소드
    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }
}
