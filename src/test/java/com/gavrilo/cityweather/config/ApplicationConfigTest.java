package com.gavrilo.cityweather.config;

import com.gavrilo.cityweather.user.User;
import com.gavrilo.cityweather.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ApplicationConfigTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private ApplicationConfig applicationConfig;

    @Test
    public void testUserDetailsService() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        UserDetailsService userDetailsService = applicationConfig.userDetailsService();
        assertNotNull(userDetailsService);

        assertEquals(User.class, userDetailsService.loadUserByUsername("test@example.com").getClass());
    }

    @Test
    public void testAuthenticationProvider() {
        when(repository.findByEmail("test@example.com")).thenReturn(Optional.of(new User()));

        AuthenticationProvider authenticationProvider = applicationConfig.authenticationProvider();
        assertNotNull(authenticationProvider);
        assertTrue(authenticationProvider instanceof DaoAuthenticationProvider);
    }

    @Test
    public void testAuthenticationManager() throws Exception {
        AuthenticationConfiguration authenticationConfiguration = mock(AuthenticationConfiguration.class);
        when(authenticationConfiguration.getAuthenticationManager()).thenReturn(mock(AuthenticationManager.class));

        AuthenticationManager authenticationManager = applicationConfig.authenticationManager(authenticationConfiguration);
        assertNotNull(authenticationManager);
    }

    @Test
    public void testPasswordEncoder() {
        PasswordEncoder passwordEncoder = applicationConfig.passwordEncoder();
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder instanceof BCryptPasswordEncoder);
    }
}