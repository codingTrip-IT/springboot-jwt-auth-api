package com.example.jwtauth.auth.controller;

import com.example.jwtauth.auth.dto.response.UserRoleUpdateResponse;
import com.example.jwtauth.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "02. 관리자 API", description = "관리자 전용 기능 (권한 부여 등)")
public class AdminController {

    private final AuthService authService;

    // 관리자 권한 부여
    @PatchMapping("/users/{userId}/roles")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "관리자 권한 부여", description = "해당 유저에게 관리자 권한을 부여합니다.")
    public ResponseEntity<UserRoleUpdateResponse> grantAdminRole(@PathVariable Long userId) {
        return ResponseEntity.ok(authService.grantAdminRole(userId));
    }
}
