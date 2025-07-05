package com.example.jwtauth.auth.authentication.jwt;

import com.example.jwtauth.common.principal.AuthUser;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

	private final AuthUser authUser;

	public JwtAuthenticationToken(AuthUser authUser) {
		super(authUser.getAuthorities());
		this.authUser = authUser;
		setAuthenticated(true);
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return authUser;
	}
}
