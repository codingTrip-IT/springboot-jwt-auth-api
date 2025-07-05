package com.example.jwtauth.auth.service;

import com.example.jwtauth.auth.authentication.jwt.JwtProvider;
import com.example.jwtauth.auth.domain.entity.User;
import com.example.jwtauth.auth.dto.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenManager {

    private final JwtProvider jwtProvider;

    public TokenResponse issueToken(User user) {
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getUsername(),
                user.getRoleName(), user.getNickname());

        return TokenResponse.of(accessToken);
    }

    public Long extractUserId(String refreshToken) {
        return jwtProvider.extractUserId(refreshToken);
    }

    public String reissueAccessToken(User user) {
        return jwtProvider.createAccessToken(user.getId(), user.getUsername(), user.getRoleName(),
                user.getNickname());
    }

}
