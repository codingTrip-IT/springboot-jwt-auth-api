package com.example.jwtauth.auth.service;

import com.example.jwtauth.auth.domain.entity.User;
import com.example.jwtauth.auth.domain.enums.UserRole;
import com.example.jwtauth.auth.dto.request.SignupRequest;
import com.example.jwtauth.auth.dto.response.SignupResponse;
import com.example.jwtauth.auth.dto.response.TokenResponse;
import com.example.jwtauth.auth.dto.response.UserRoleUpdateResponse;
import com.example.jwtauth.auth.exception.AuthErrorCode;
import com.example.jwtauth.common.exception.JwtAuthException;
import com.example.jwtauth.auth.exception.UserErrorCode;
import com.example.jwtauth.auth.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenManager tokenManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public SignupResponse registerUser(SignupRequest request) {

        validateDuplicateUser(request.getUsername());

        String encodedPassword = bCryptPasswordEncoder.encode(request.getPassword());

        User user = User.createUser(request.getUsername(), encodedPassword, request.getNickname());

        userRepository.save(user);

        return SignupResponse.of(
                user.getId(),
                user.getUsername(),
                user.getNickname(),
                user.getRoleName()
        );
    }

    @Transactional
    public TokenResponse login(String username, String password) {

        User user = userRepository.findByUsernameAndDeletedAtIsNull(username).orElseThrow(
                () -> new JwtAuthException(UserErrorCode.USERNAME_NOT_FOUND)
        );

        validatePassword(password, user.getPassword());

        return tokenManager.issueToken(user);
    }

    public UserRoleUpdateResponse grantAdminRole(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new JwtAuthException(UserErrorCode.USER_NOT_FOUND)
        );

        user.updateRole(UserRole.ROLE_ADMIN);  // enum 값
        return UserRoleUpdateResponse.from(user);
    }


    private void validateDuplicateUser(String username) {
        if (existsUser(username)) {
            throw new JwtAuthException(AuthErrorCode.DUPLICATED_USERNAME);
        }
    }

    public boolean existsUser(String username) {
        return userRepository.existsByUsername(username);
    }

    private void validatePassword(String password, String encodedPassword) {
        if (!bCryptPasswordEncoder.matches(password, encodedPassword)) {
            throw new JwtAuthException(AuthErrorCode.INVALID_PASSWORD);
        }
    }
}
