package com.dubbi.blogplatform.application.aspect;

import com.dubbi.blogplatform.application.factory.MockSecurityContextFactory;
import com.dubbi.blogplatform.enumeratedclasses.Role;
import com.dubbi.blogplatform.enumeratedclasses.SocialType;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = MockSecurityContextFactory.class)
public @interface MockMember {
    long id() default 1L;

    long age() default 99L;

    String city() default "Seoul";

    String email() default "user@example.com";

    String nickname() default "Test Nickname";

    String password() default "password";

    String profileImageUrl() default "http://www.testurl.com";

    String profileImageFileName() default "test file name";

    String refreshToken() default "validRefreshToken";

    Role role() default Role.USER;  // REPLACE WITH ACTUAL DEFAULT ROLE

    SocialType socialType() default SocialType.NONE; // REPLACE WITH ACTUAL DEFAULT SOCIAL TYPE

    String socialId() default "defaultSocialId";
}
