package com.gavrilo.cityweather.auth;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationResponseTest {

    @Test
    void authenticationResponseBuilder() {
        AuthenticationResponse response = AuthenticationResponse.builder()
                .token("testToken")
                .build();

        assertNotNull(response);
        assertEquals("testToken", response.getToken());
    }

    @Test
    void authenticationResponseGettersAndSetters() {
        AuthenticationResponse response = new AuthenticationResponse();
        response.setToken("testToken");

        assertEquals("testToken", response.getToken());
    }
}