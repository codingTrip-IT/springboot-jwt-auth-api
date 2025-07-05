package com.example.jwtauth.auth.authentication.filter;

import com.example.jwtauth.auth.authentication.jwt.JwtAuthenticationToken;
import com.example.jwtauth.auth.authentication.jwt.JwtProvider;
import com.example.jwtauth.common.principal.AuthUser;
import com.example.jwtauth.auth.exception.AuthErrorCode;
import com.example.jwtauth.common.exception.JwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.jwtauth.auth.authentication.jwt.JwtProvider.AUTHORIZATION_HEADER;
import static com.example.jwtauth.auth.authentication.jwt.JwtProvider.BEARER_PREFIX;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		String authorizationHeader = request.getHeader(AUTHORIZATION_HEADER);
		if (authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)) {
			String accessToken = jwtProvider.subStringToken(authorizationHeader);

			try {
				Claims claims = jwtProvider.extractClaims(accessToken);

				if (SecurityContextHolder.getContext().getAuthentication() == null) {
					setAuthentication(claims);
				}
			} catch (SecurityException | MalformedJwtException e) {
				log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.", e);
				throw new JwtAuthException(AuthErrorCode.INVALID_JWT_SIGNATURE);
			} catch (ExpiredJwtException e) {
				log.error("Expired JWT token, 만료된 JWT token 입니다.", e);
				throw new JwtAuthException(AuthErrorCode.TOKEN_EXPIRED);
			} catch (UnsupportedJwtException e) {
				log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.", e);
				throw new JwtAuthException(AuthErrorCode.UNSUPPORTED_TOKEN);
			} catch (Exception e) {
				log.error("Internal server error", e);
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			}
		}

		filterChain.doFilter(request, response);
	}

	private void setAuthentication(Claims claims) {
		Long userId = Long.valueOf(claims.getSubject());
		String username = claims.get("username", String.class);
		String roleName = claims.get("userRole", String.class);
		String nickname = claims.get("nickname", String.class);
		AuthUser authUser = AuthUser.builder()
			.id(userId)
			.username(username)
			.roleName(roleName)
			.nickname(nickname)
			.build();
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken(authUser);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);
	}
}
