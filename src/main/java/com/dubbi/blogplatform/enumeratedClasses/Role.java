package com.dubbi.blogplatform.enumeratedClasses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN","관리자"),
    GUEST("ROLE_GUEST","게스트"),
    USER("ROLE_USER","회원");

    private final String key;
    private final String title;
}
