package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.domain.entity.User;
import com.dubbi.blogplatform.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("해당 이메일이 존재하지 않습니다."));

        /**아래의 return문에서는 springframework security의 userdetails를 반환해야 하기 때문에, User를 import 해오는 위치가 다름.
         * User가 가져야 하는 필드는 username, password, authorities 3종류이므로 아래 username, password, roles 3가지를 확인한다.
         * public UserBuilder roles(String... roles) {
         *     List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
         *     for (String role : roles) {
         *         Assert.isTrue(!role.startsWith("ROLE_"),
         *                 () -> role + " cannot start with ROLE_ (it is automatically added)");
         *         authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
         *     }
         *     return authorities(authorities);
         * }
         * User builder에는 위와 같이 user의 role을 확인하는 함수가 숨어있으므로, Authority에 ROLE_이라는 접두사가 붙어있는지 확인하고 넘어간다.*/
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRoleKey().substring(5))
                .build();
    }
}
