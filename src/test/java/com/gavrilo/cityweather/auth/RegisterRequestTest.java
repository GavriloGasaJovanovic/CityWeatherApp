package com.gavrilo.cityweather.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RegisterRequestTest {

    @Test
    void registerRequestBuilder() {
        RegisterRequest request = RegisterRequest.builder()
                .email("test@example.com")
                .password("password")
                .build();

        assertNotNull(request);
        assertEquals("test@example.com", request.getEmail());
        assertEquals("password", request.getPassword());
    }

    @Test
    void registerRequestGettersAndSetters() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@example.com");
        request.setPassword("password");

        assertEquals("test@example.com", request.getEmail());
        assertEquals("password", request.getPassword());
    }
}