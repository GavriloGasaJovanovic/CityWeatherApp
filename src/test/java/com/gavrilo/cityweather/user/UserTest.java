package com.gavrilo.cityweather.user;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserTest {

    @Test
    void userBuilder() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertEquals(1, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void userGettersAndSetters() {
        User user = new User();
        user.setId(1);
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole(Role.USER);

        assertEquals(1, user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(Role.USER, user.getRole());
    }

    @Test
    void userAuthorities() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertEquals(1, user.getAuthorities().size());
        assertTrue(user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(Role.USER.name())));
    }

    @Test
    void userGetUsername() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertEquals("test@example.com", user.getUsername());
    }

    @Test
    void userAccountNonExpired() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void userAccountNonLocked() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void userCredentialsNonExpired() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void userIsEnabled() {
        User user = User.builder()
                .id(1)
                .email("test@example.com")
                .password("password")
                .role(Role.USER)
                .build();

        assertNotNull(user);
        assertTrue(user.isEnabled());
    }
}