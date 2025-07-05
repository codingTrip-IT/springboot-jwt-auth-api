package com.example.jwtauth.auth.dto.response;

import com.example.jwtauth.auth.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class UserRoleUpdateResponse {

    private final String username;
    private final String nickname;
    private final List<RoleDto> roles;

    @Builder
    public UserRoleUpdateResponse(String username, String nickname, List<RoleDto> roles) {
        this.username = username;
        this.nickname = nickname;
        this.roles = roles;
    }

    public static UserRoleUpdateResponse from(User user) {
        return UserRoleUpdateResponse.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .roles(List.of(new RoleDto(user.getRole().name().replace("ROLE_", ""))))  // Admin
                .build();
    }

    @Getter
    public static class RoleDto {
        private final String role;

        public RoleDto(String role) {
            this.role = role;
        }
    }
}