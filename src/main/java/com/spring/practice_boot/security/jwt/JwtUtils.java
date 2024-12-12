package com.spring.practice_boot.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;

@Component
@Configurable
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    public static final String JWT_SIGNING_ALGORITHM = "HmacSHA256";
    public static final String AUTHORIZATION_PREFIX = "Bearer ";

    @Value("${springjwt.app.jwtSecret}")
    private String jwtSecret;

    @Value("${springjwt.app.jwtExpirationMs}")
    private int jwtExpirationMs;


    public String getJwtFromAuthorizationHeader(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (headerAuth != null && headerAuth.startsWith(AUTHORIZATION_PREFIX)) {
            return headerAuth.substring(7);
        }
        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            if (authToken.startsWith(AUTHORIZATION_PREFIX)) {
                // Strip "Bearer " prefix if present
                authToken = authToken.substring(7);
            }

            byte[] apiKeySecretBytes = jwtSecret.getBytes();
            SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, JWT_SIGNING_ALGORITHM);
            Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedKeyException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
            return false;
        }
        return false;
    }

    public String getUserNameFromJwtToken(String token) {
        byte[] apiKeySecretBytes = jwtSecret.getBytes();
        SecretKey signingKey = new SecretKeySpec(apiKeySecretBytes, JWT_SIGNING_ALGORITHM);
        return Jwts.parser().verifyWith(signingKey).build().
                parseSignedClaims(token).getPayload().getSubject();
    }

    public String generateTokenFromUsername(String username) {

        byte[] apiKeySecretBytes = jwtSecret.getBytes();
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, JWT_SIGNING_ALGORITHM);
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs)).signWith(signingKey).compact();
    }
}
