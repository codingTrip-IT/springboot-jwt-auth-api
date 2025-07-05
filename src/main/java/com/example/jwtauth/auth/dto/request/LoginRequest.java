package com.example.jwtauth.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "로그인 요청")
public class LoginRequest {

    @NotBlank(message = "username은 필수 입력 항목입니다.")
    @Schema(description = "username", example = "userA")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Schema(description = "비밀번호", example = "1Q2w3e4r!")
    private String password;

    public LoginRequest() {
    }

    @Builder
    private LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
