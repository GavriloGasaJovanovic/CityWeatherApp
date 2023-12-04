package com.gavrilo.cityweather.config;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtServiceTest {

    private final JwtService jwtService = new JwtService();

    @Test
    public void testExtractUserEmail() {
        String token = jwtService.generateToken(createUserDetails());
        String userEmail = jwtService.extractUserEmail(token);
        assertEquals("test@example.com", userEmail);
    }

    @Test
    public void testExtractClaim() {
        String token = jwtService.generateToken(createUserDetails());
        String subject = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals("test@example.com", subject);
    }

    @Test
    public void testGenerateToken() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        assertNotNull(token);
    }

    @Test
    public void testIsTokenValid() {
        UserDetails userDetails = createUserDetails();
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }

    @Test
    public void testExtractExpiration() {
        Date expirationDate = new Date(System.currentTimeMillis() + 1000);
        String token = jwtService.generateToken(createUserDetails(), expirationDate);
        assertNotEquals(expirationDate, jwtService.extractExpiration(token));
    }

    @Test
    public void testExtractAllClaims() {
        String token = jwtService.generateToken(createUserDetails());
        Claims claims = jwtService.extractAllClaims(token);
        assertNotNull(claims);
        assertEquals("test@example.com", claims.getSubject());
    }

    private UserDetails createUserDetails() {
        return User.builder()
                .username("test@example.com")
                .password("password")
                .roles("USER")
                .build();
    }
}