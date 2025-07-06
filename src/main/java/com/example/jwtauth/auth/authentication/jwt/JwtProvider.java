package com.example.jwtauth.auth.authentication.jwt;

import com.example.jwtauth.auth.exception.AuthErrorCode;
import com.example.jwtauth.common.exception.JwtAuthException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtProvider {

    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    private final SecretKey signingKey;
    private final JwtProperties jwtProperties;

    public JwtProvider(JwtProperties jwtProperties) {
        byte[] bytes = Base64.getDecoder().decode(jwtProperties.getSecretKey());
        this.signingKey = Keys.hmacShaKeyFor(bytes);
        this.jwtProperties = jwtProperties;
    }

    public String createAccessToken(Long userId, String username, String roleName, String nickname) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(String.valueOf(userId)) // ✅ 여기
                        .claim("username", username)
                        .claim("userRole", roleName)
                        .claim("nickname", nickname)
                        .setExpiration(createExpiration(jwtProperties.getAccessTokenExpiration()))
                        .setIssuedAt(date)
                        .signWith(this.signingKey) // 필요 시 알고리즘도 지정
                        .compact();

    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(this.signingKey)
                .setAllowedClockSkewSeconds(60) // 60초 허용
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String subStringToken(String tokenValue) {
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(BEARER_PREFIX.length());
        }
        throw new JwtAuthException(AuthErrorCode.TOKEN_NOT_FOUND);
    }

    public Long extractUserId(String refreshToken) {
        try {
            Claims claims = extractClaims(refreshToken);
            String subject = claims.getSubject();
            if (subject == null) {
                throw new JwtAuthException(AuthErrorCode.INVALID_JWT_SIGNATURE);
            }
            return Long.valueOf(subject);
        }catch (JwtException | IllegalArgumentException e) {
            log.error("[JWT 파싱 실패] 잘못된 토큰입니다. token={}", refreshToken, e);
            throw new JwtAuthException(AuthErrorCode.INVALID_JWT_SIGNATURE);
        }

    }

    private Date createExpiration(long tokenTime) {
        return new Date(System.currentTimeMillis() + tokenTime);
    }
}
