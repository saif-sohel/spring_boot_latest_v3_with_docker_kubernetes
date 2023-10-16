package com.spring.practice_boot.security.jwt;

import com.spring.practice_boot.security.services.EmployeeDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.MalformedKeyException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Configurable
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${springjwt.app.jwtSecret}")
    private String jwtSecret;

    @Value("${springjwt.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Value("${springjwt.app.jwtCookieName}")
    private String jwtCookie;


    public String getJwtFromCookies(HttpServletRequest request)
    {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if (cookie != null)
        {
            return cookie.getValue();
        } else
        {
            return null;
        }
    }

    public ResponseCookie generateJwtCookie(EmployeeDetailsImpl employeeDetails) throws UnsupportedEncodingException {
        String jwt = generateTokenFromUsername(employeeDetails.getUsername());
        return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(24 * 60 * 60).httpOnly(true).build();
    }

    public ResponseCookie getCleanJwtCookie()
    {
        return ResponseCookie.from(jwtCookie, "").path("/api").build();
    }

    public String getUserNameFromJwtToken(String token)
    {
        return Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).build().
                parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jws<Claims> jwt = Jwts.parser().setSigningKey(jwtSecret.getBytes(StandardCharsets.UTF_8)).build().parseSignedClaims(authToken);
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

    public String generateTokenFromUsername(String username) throws UnsupportedEncodingException {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret.getBytes(StandardCharsets.UTF_8))
                .compact();
    }
}
