package com.example.jwtauth.auth.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 응답 DTO")
public class SignupResponse {

    @Schema(description = "유저 ID", example = "1")
    private final Long id;

    @Schema(description = "username", example = "userA")
    private final String username;

    @Schema(description = "닉네임", example = "ABC")
    private final String nickname;

    @Schema(description = "권한", example = "User")
    private final String userRole;

    @Builder
    private SignupResponse(Long id, String username, String nickname, String userRole) {
        this.id = id;
        this.username = username;
        this.nickname = nickname;
        this.userRole = userRole;
    }

    public static SignupResponse of(Long id, String username, String nickname, String userRole) {
        return SignupResponse.builder()
                .id(id)
                .username(username)
                .nickname(nickname)
                .userRole(userRole)
                .build();
    }
}