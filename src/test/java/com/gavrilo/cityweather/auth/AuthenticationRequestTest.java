package com.gavrilo.cityweather.auth;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthenticationRequestTest {

    @Test
    void authenticationRequestBuilder() {
        AuthenticationRequest request = AuthenticationRequest.builder()
                .email("test@example.com")
                .password("testPassword")
                .build();

        assertNotNull(request);
        assertEquals("test@example.com", request.getEmail());
        assertEquals("testPassword", request.getPassword());
    }

    @Test
    void authenticationRequestGettersAndSetters() {
        AuthenticationRequest request = new AuthenticationRequest();
        request.setEmail("test@example.com");
        request.setPassword("testPassword");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("testPassword", request.getPassword());
    }
}
