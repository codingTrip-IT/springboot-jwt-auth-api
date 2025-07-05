package com.example.jwtauth.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "로그인 응답 DTO")
public class LoginResponse {

    @Schema(description = "발급된 액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private final String accessToken;

    @Builder
    private LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }

    public static LoginResponse of(TokenResponse tokenResponse) {
        return LoginResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .build();
    }
}