package com.dubbi.blogplatform.config;

import com.dubbi.blogplatform.authentication.service.DubbiOAuth2UserService;
import com.dubbi.blogplatform.authorization.role.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DubbiOAuth2UserService dubbiOAuth2UserService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .headers((headerConfig) -> headerConfig.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .requestMatchers("/Landing", "/resources/**", "/static/**", "/login").permitAll()
                                .requestMatchers("/posts/**", "/api/v1/posts/**").hasRole(Role.USER.name())
                                .requestMatchers("/admins/**", "/api/v1/admins/**").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated()
                )
                .logout((logoutConfig) ->
                        logoutConfig.logoutSuccessUrl("/")
                )
                .oauth2Login((oauth2Login) ->
                        oauth2Login
                                .defaultSuccessUrl("/",true)
                                .userInfoEndpoint(userInfo -> {
                                    userInfo
                                    .userService(dubbiOAuth2UserService);
                        }));

        return http.build();
    }
}

