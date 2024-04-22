package com.dubbi.blogplatform.application.service.implementation;

import com.dubbi.blogplatform.authentication.application.dto.UserSignUpDto;
import com.dubbi.blogplatform.authentication.application.service.implementation.UserServiceImpl;
import com.dubbi.blogplatform.authentication.domain.entity.User;
import com.dubbi.blogplatform.authentication.domain.repository.UserRepository;
import com.dubbi.blogplatform.common.enumeratedclasses.Role;
import com.dubbi.blogplatform.common.enumeratedclasses.SocialType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@DisplayName("UserServiceImpl Unit Test")
public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserSignUpDto userSignUpDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userSignUpDto = UserSignUpDto.builder()
                .email("test@gmail.com")
                .nickname("nick")
                .password("password")
                .age(22L)
                .city("New York").build();
    }

    @DisplayName("Test sign up with valid user")
    @Test
    void testSignUp() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByNickname(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("password");

        User expectedUser = User.builder()
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .nickname(userSignUpDto.getNickname())
                .age(userSignUpDto.getAge())
                .city(userSignUpDto.getCity())
                .role(Role.USER)
                .socialType(SocialType.NONE)
                .build();

        when(userRepository.save(any())).thenReturn(expectedUser);

        // When
        User actualUser = userServiceImpl.signUp(userSignUpDto);

        // Then
        assertNotNull(actualUser);
        assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        assertEquals(expectedUser.getNickname(), actualUser.getNickname());
        assertEquals(expectedUser.getAge(), actualUser.getAge());
        assertEquals(expectedUser.getCity(), actualUser.getCity());
    }

    @DisplayName("Test sign up with existing email")
    @Test
    void testSignUpWithExistingEmail() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(new User()));

        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.signUp(userSignUpDto));
    }

    @DisplayName("Test sign up with existing nickname")
    @Test
    void testSignUpWithExistingNickname() {
        // Given
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(userRepository.findByNickname(anyString())).thenReturn(Optional.of(new User()));

        // When
        // Then
        assertThrows(IllegalArgumentException.class, () -> userServiceImpl.signUp(userSignUpDto));
    }
}