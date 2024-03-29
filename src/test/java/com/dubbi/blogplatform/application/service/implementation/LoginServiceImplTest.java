package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.domain.entity.User;
import com.dubbi.blogplatform.domain.repository.UserRepository;
import com.dubbi.blogplatform.enumeratedclasses.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LoginServiceImplTest {
    
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final LoginServiceImpl loginService = new LoginServiceImpl(userRepository);

    @Test
    @DisplayName("Test successfully loading user by username/email")
    void loadUserByUsernameSuccessful() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setPassword("password123");
        user.setRole(Role.USER);

        Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        UserDetails userDetails = loginService.loadUserByUsername(user.getEmail());

        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
        assertEquals(user.getRoleKey().substring(5), userDetails.getAuthorities().iterator().next().getAuthority().substring(5));
    }

    @Test
    @DisplayName("Test unsuccessful attempt to load user by username/email")
    void loadUserByUsernameUnsuccessful() {
        String nonExistentEmail = "nonexistent@email.com";
        
        Mockito.when(userRepository.findByEmail(nonExistentEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> loginService.loadUserByUsername(nonExistentEmail));
    }
}