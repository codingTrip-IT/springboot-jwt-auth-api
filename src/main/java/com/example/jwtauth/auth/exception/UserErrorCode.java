package com.example.jwtauth.auth.exception;

import com.example.jwtauth.common.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum UserErrorCode implements ErrorCode {

    // 400 Bad Request
    USERNAME_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER_001", "존재하지 않는 username입니다."),
    USER_PASSWORD_SAME_AS_OLD(HttpStatus.BAD_REQUEST, "USER_005", "새 비밀번호는 기존 비밀번호와 같을 수 없습니다."),
    INVALID_USER_ROLE(HttpStatus.BAD_REQUEST, "USER_006", "유효하지 않은 Role 입니다."),
    INVALID_USER_ROLE_STATE(HttpStatus.BAD_REQUEST,"USER_007","유효하지 않은 사용자 권한 정보입니다."),

    // 404 Not Found
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_002", "유저를 찾을 수 없습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String defaultMessage;
}
