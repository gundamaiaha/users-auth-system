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
        boolean isAuthenticationValid = userAuthService.login("another", "the loose");
        Assertions.assertTrue(isAuthenticationValid);
    }

    @Test
    void testLoginForInvalidCredentials() {
        boolean isAuthenticationValid = userAuthService.login("test", "test");
        Assertions.assertFalse(isAuthenticationValid);
    }
}
