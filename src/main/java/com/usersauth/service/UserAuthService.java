package com.usersauth.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.usersauth.exception.AuthenticationException;
import com.usersauth.model.Domain;
import com.usersauth.model.User;

import java.util.*;

/**
 * UserAuthService is a service which will authenticate the users
 * and return the right of the users as per the domain user passed.
 */
public class UserAuthService {

    private static final Map<String, User> usersMap = new HashMap<>();
    private static final Map<String, List<Domain>> roleDomainsMap = new HashMap<>();

    static {
        buildUsersData();
    }

    /**
     * Method to add the test data for testing
     *
     */
    private static void buildUsersData() {
        //domains
        Domain adminGradingDomain = new Domain("GRADING", Arrays.asList("READ", "WRITE"));
        Domain adminCourseFeedBackDomain = new Domain("COURSE_FEEDBACK", Arrays.asList("READ", "WRITE"));
        Domain studentGradingDomain = new Domain("GRADING", Arrays.asList("READ"));
        Domain studentCourseFeedBackDomain = new Domain("COURSE_FEEDBACK", Arrays.asList("READ", "WRITE"));
        Domain teacherGradingDomain = new Domain("GRADING", Arrays.asList("READ", "WRITE"));
        Domain teacherCourseFeedBackDomain = new Domain("COURSE_FEEDBACK", Arrays.asList("READ"));


        User another = new User("another", "the loose", "ADMIN");
        User berit = new User("berit", "123456", "STUDENT");
        User call = new User("call", "password", "TEACHER");


        roleDomainsMap.put("ADMIN", Arrays.asList(adminGradingDomain, adminCourseFeedBackDomain));
        roleDomainsMap.put("STUDENT", Arrays.asList(studentGradingDomain, studentCourseFeedBackDomain));
        roleDomainsMap.put("TEACHER", Arrays.asList(teacherGradingDomain, teacherCourseFeedBackDomain));

        usersMap.put("another", another);
        usersMap.put("berit", berit);
        usersMap.put("call", call);
    }


    /**
     * Method which will take the username and password and
     * return the token if user is valid one else
     * throw AuthenticationException
     * @param username
     * @param password
     * @return token
     */
    public String login(String username, String password) {
        User user = usersMap.get(username);
        if (user != null && user.getPassword().equals(password)) {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            return JWT.create()
                    .withSubject(username)
                    .withClaim("role", user.getRole())
                    .sign(algorithm);
        } else {
            throw new AuthenticationException("Invalid username or password");
        }
    }

    /**
     * Method that will take the token(JWT) and domain
     * if the token is valid, then this method will return
     * the entitlements(rights) of the user else
     * will throw AuthenticationException
     * @param token
     * @param domain
     * @return rights
     */
    public List<String> getUserRights(String token,String domain){
        List<String> userRights = new ArrayList<>();
        try {
            // get claims
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT decodedJWT = verifier.verify(token);

            String role = decodedJWT.getClaim("role").asString();
            List<Domain> domains = roleDomainsMap.get(role);

            Optional<Domain> domainOptional = domains.stream().filter(userDomain -> userDomain.getDomain().equals(domain)).findFirst();
            if (domainOptional.isPresent()) {
                userRights = domainOptional.get().getEntitlements();
            }
        }catch (Exception exception){
            throw new AuthenticationException("Invalid token");
        }
        return userRights;
    }
}
