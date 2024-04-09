package com.dubbi.blogplatform.application.factory;

import com.dubbi.blogplatform.application.aspect.MockMember;
import com.dubbi.blogplatform.post.domain.entity.Image;
import com.dubbi.blogplatform.authentication.domain.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class MockSecurityContextFactory implements WithSecurityContextFactory<MockMember> {
    @Override
    public SecurityContext createSecurityContext(MockMember annotation) {
        User mockUser = User.builder()
                .id(1L)
                .age(annotation.age())
                .city(annotation.city())
                .email(annotation.email())
                .password(annotation.password())
                .nickname(annotation.nickname())
                .profilePicture(Image.builder().id(1L).fileName(annotation.profileImageFileName()).url(annotation.profileImageUrl()).imagePrompt("TestImage").build())
                .refreshToken(annotation.refreshToken())
                .role(annotation.role())
                .socialType(annotation.socialType())
                .socialId(annotation.socialId()).build();

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(mockUser.getEmail(),mockUser.getPassword());
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(token);
        return context;
    }
}
