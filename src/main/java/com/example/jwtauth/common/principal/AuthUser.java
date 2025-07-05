package com.example.jwtauth.common.principal;

import com.example.jwtauth.common.exception.CommonErrorCode;
import com.example.jwtauth.common.exception.JwtAuthException;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {
	private final Long id;
	private final String username;
	private final Collection<? extends GrantedAuthority> authorities;
	private final String nickname;

	@Builder
	private AuthUser(Long id, String username, String roleName, String nickname) {
		this.id = id;
		this.username = username;
		this.authorities = List.of(new SimpleGrantedAuthority(roleName));
		this.nickname = nickname;
	}

	public String getFirstAuthority() {
		return this.authorities.stream()
			.findFirst()
			.orElseThrow(() -> new JwtAuthException(CommonErrorCode.AUTHORITY_NOT_FOUND))
			.getAuthority();
	}

	public boolean isMine(Long dtoUserId, Long authUserId) {
		return dtoUserId.equals(authUserId);
	}
}
