package com.example.jwtauth.auth.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청")
public class SignupRequest {

    @NotBlank(message = "username은 필수 입력 항목입니다.")
    @Schema(description = "username", example = "userA")
    private String username;

    @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&]).{8,}$"
            , message = "대문자, 숫자, 특수문자(!,@,#,$,%,^,&)를 최소 1개 이상 포함한 8자리 이상으로 입력해주세요.")
    @Schema(description = "비밀번호", example = "1Q2w3e4r!")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 항목입니다.")
    @Schema(description = "사용자 닉네임", example = "ABC")
    private String nickname;

    public SignupRequest() {
    }

    @Builder
    private SignupRequest(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }
}
