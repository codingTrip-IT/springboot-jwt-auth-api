package com.example.jwtauth.auth.service;

import com.example.jwtauth.auth.domain.entity.User;
import com.example.jwtauth.auth.domain.enums.UserRole;
import com.example.jwtauth.auth.domain.repository.UserRepository;
import com.example.jwtauth.auth.dto.request.LoginRequest;
import com.example.jwtauth.auth.dto.request.SignupRequest;
import com.example.jwtauth.auth.dto.response.TokenResponse;
import com.example.jwtauth.common.exception.JwtAuthException;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenManager tokenManager;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    SignupRequest signupRequest = SignupRequest.builder()
            .username("testuser")
            .password("1234")
            .nickname("테스트유저")
            .build();

    LoginRequest loginRequest = LoginRequest.builder()
            .username("testuser")
            .password("1234")
            .build();

    @Nested
    class SignupUser {
        @Test
        void 회원가입_정상_성공() {
            given(userRepository.existsByUsername("testuser")).willReturn(false);
            given(passwordEncoder.encode("1234")).willReturn("encoded_pw");

            authService.registerUser(signupRequest);

            then(userRepository).should().save(any(User.class));
        }

        @Test
        void 회원가입_중복아이디_예외() {
            given(userRepository.existsByUsername("testuser")).willReturn(true);

            JwtAuthException exception = assertThrows(JwtAuthException.class,
                    () -> authService.registerUser(signupRequest));

            assertEquals("이미 가입된 username입니다.", exception.getMessage());
        }
    }

    @Nested
    class LoginUser {
        @Test
        void 로그인_정상_성공() {
            User user = User.createUser("testuser", "encoded_pw", "테스트유저");
            given(userRepository.findByUsernameAndDeletedAtIsNull("testuser")).willReturn(Optional.of(user));
            given(passwordEncoder.matches("1234", "encoded_pw")).willReturn(true);
            given(tokenManager.issueToken(user)).willReturn(TokenResponse.of("dummyToken"));

            TokenResponse response = authService.login(loginRequest.getUsername(), loginRequest.getPassword());

            assertNotNull(response);
            assertEquals("dummyToken", response.getAccessToken());
        }

        @Test
        void 로그인_아이디_찾을_수_없음() {
            // given
            given(userRepository.findByUsernameAndDeletedAtIsNull("wrongUser"))
                    .willReturn(Optional.empty());

            // when & then
            assertThrows(JwtAuthException.class, () ->
                    authService.login("wrongUser", "1234")
            );
        }


        @Test
        void 로그인_비밀번호_틀림() {
            LoginRequest wrongPwLoginRequest = LoginRequest.builder()
                    .username("testuser")
                    .password("wrongpw")
                    .build();

            User user = User.createUser("testuser", "encoded_pw", "테스트유저");
            given(userRepository.findByUsernameAndDeletedAtIsNull("testuser")).willReturn(Optional.of(user));
            given(passwordEncoder.matches("wrongpw", "encoded_pw")).willReturn(false);

            assertThrows(JwtAuthException.class, () -> authService.login(wrongPwLoginRequest.getUsername(),wrongPwLoginRequest.getPassword()));
        }
    }

    @Nested
    class AdminUser {
        @Test
        void 관리자_권한_정상_부여() {
            Long userId = 1L;
            User user = User.createUser("user", "pw", "유저");
            given(userRepository.findById(userId)).willReturn(Optional.of(user));

            authService.grantAdminRole(userId);

            assertEquals(UserRole.ROLE_ADMIN, user.getRole());
        }

        @Test
        void 관리자_권한_없는_사용자_예외() {
            Long userId = -1L;
            given(userRepository.findById(userId)).willReturn(Optional.empty());

            assertThrows(JwtAuthException.class, () -> authService.grantAdminRole(userId));
        }
    }
}
