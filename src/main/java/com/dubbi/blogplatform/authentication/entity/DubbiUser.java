package com.dubbi.blogplatform.authentication.entity;

import com.dubbi.blogplatform.authorization.role.Role;
import com.dubbi.blogplatform.common.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class DubbiUser extends BaseEntity {
    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "E_MAIL")
    private String email;

    @Column(name = "PROFILE_PICTURE")
    private String picture;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "nickname", unique = true)
    private String nickname;

    @Column(name = "ROLE")
    @Enumerated(EnumType.STRING)
    private Role role;

    public DubbiUser update(String userName, String picture){
        this.userName = userName;
        this.picture = picture;
        return this;
    }

    public String getRoleKey(){
        return this.role.getKey();
    }
}
