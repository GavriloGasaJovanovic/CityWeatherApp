package com.gavrilo.cityweather.auth;

import com.gavrilo.cityweather.config.JwtService;
import com.gavrilo.cityweather.user.User;
import com.gavrilo.cityweather.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class AuthenticationServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    @Test
    public void testRegister_Success() {
        RegisterRequest request = new RegisterRequest("test@example.com", "password123");
        when(passwordEncoder.encode(request.getPassword())).thenReturn("encodedPassword");
        when(repository.save(any())).thenReturn(new User());

        assertDoesNotThrow(() -> {
            AuthenticationResponse response = authenticationService.register(request);

            assertNotNull(response);
        });
    }

    @Test
    public void testLogin_Success() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password123");
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(repository.findByEmail(request.getEmail())).thenReturn(Optional.of(new User()));
        when(jwtService.generateToken(any())).thenReturn("jwtToken");

        assertDoesNotThrow(() -> {
            AuthenticationResponse response = authenticationService.login(request);

            assertNotNull(response);
            assertNotNull(response.getToken());
        });
    }

    @Test
    public void testLogin_AuthenticationException() {
        AuthenticationRequest request = new AuthenticationRequest("test@example.com", "password123");
        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Test exception") {
        });

        AuthenticationException exception = assertThrows(AuthenticationException.class, () -> {
            AuthenticationResponse response = authenticationService.login(request);
        });

        assertEquals("Test exception", exception.getMessage());
    }
}