package com.example.jwtauth.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtAuthException extends RuntimeException {
    private final ErrorCode errorCode;

    public HttpStatus getStatus() {
        return errorCode.getHttpStatus();
    }

    public JwtAuthException(ErrorCode errorCode) {
        super(errorCode.getDefaultMessage());
        this.errorCode = errorCode;
    }
}
