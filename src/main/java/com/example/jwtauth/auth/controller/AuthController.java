package com.example.jwtauth.auth.controller;

import com.example.jwtauth.auth.dto.request.LoginRequest;
import com.example.jwtauth.auth.dto.request.SignupRequest;
import com.example.jwtauth.auth.dto.response.SignupResponse;
import com.example.jwtauth.auth.dto.response.TokenResponse;
import com.example.jwtauth.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "01. 인증 API", description = "회원가입 및 로그인을 처리합니다.")
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "사용자 회원가입", description = "사용자의 회원가입을 처리합니다.")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        return ResponseEntity.ok(authService.registerUser(request));
    }

    @Operation(summary = "로그인", description = "로그인을 처리합니다.")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request.getUsername(), request.getPassword()));
    }

}
