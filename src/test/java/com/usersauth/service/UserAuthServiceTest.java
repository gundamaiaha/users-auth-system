package com.usersauth.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.AuthenticationException;
import java.util.Collections;
import java.util.List;

class UserAuthServiceTest {
    private UserAuthService userAuthService;

    @BeforeEach
    void setup() {
        userAuthService = new UserAuthService();
    }

    @Test
    void testLogin() {
        String token = userAuthService.login("another", "the loose");
        //get claims
        Algorithm algorithm = Algorithm.HMAC256("secret");
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        DecodedJWT decodedJWT = verifier.verify(token);

        String role = decodedJWT.getClaim("role").asString();
        Assertions.assertNotNull(token);
        Assertions.assertNotNull(role);
        Assertions.assertEquals("ADMIN", role);
    }

    @Test
    void testLoginForInvalidCredentials() {

        AuthenticationException authenticationException = Assertions.assertThrows(AuthenticationException.class,
                () -> userAuthService.login("test", "test"));

        Assertions.assertEquals("Invalid username or password", authenticationException.getMessage());
    }


    @Test
    void testGetUserRights(){
        String token = userAuthService.login("another", "the loose");
        List<String> userRights = userAuthService.getUserRights(token,"GRADING");
        Assertions.assertNotEquals(Collections.EMPTY_LIST, userRights);
        Assertions.assertTrue(userRights.contains("READ"));
        Assertions.assertTrue(userRights.contains("WRITE"));
    }

    @Test
    void testGetUserRightsWithInvalidCredentials(){
        AuthenticationException authenticationException = Assertions.assertThrows(AuthenticationException.class,
                () -> userAuthService.getUserRights("invalid_token","GRADING"));
        Assertions.assertEquals("Invalid token", authenticationException.getMessage());
    }
}
