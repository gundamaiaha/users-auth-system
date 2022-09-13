package com.usersauth.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserAuthServiceTest {
    private UserAuthService userAuthService;

    @BeforeEach
    void setup() {
        userAuthService = new UserAuthService();
    }

    @Test
    void testLogin() {
        String token = userAuthService.login("another", "the loose");
        Assertions.assertNotNull(token);
    }

    @Test
    void testLoginForInvalidCredentials() {
        String token = userAuthService.login("test", "test");
        Assertions.assertNull(token);
    }
}
