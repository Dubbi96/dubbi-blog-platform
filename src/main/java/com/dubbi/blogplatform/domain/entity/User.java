package com.dubbi.blogplatform.domain.entity;

import com.dubbi.blogplatform.application.dto.OAuthEnrollDto;
import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import com.dubbi.blogplatform.enumeratedclasses.Role;
import com.dubbi.blogplatform.enumeratedclasses.SocialType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User extends BaseEntity {

    @Column(name = "age")
    private Long age;

    @Column(name = "city")
    private String city;

    @Column(name = "e_mail")
    private String email;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "password")
    private String password;

    @ManyToOne
    @JoinColumn(name = "profile_picture")
    private Image profilePicture;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(name = "social_type")
    private SocialType socialType;

    @Column(name = "social_id")
    private String socialId;

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

    public void oauthEnrollUpdate(OAuthEnrollDto oAuthEnrollDto){
        this.email = oAuthEnrollDto.getEmail();
        this.age = oAuthEnrollDto.getAge();
        this.city = oAuthEnrollDto.getCity();
        this.role = Role.USER;
    }
}
