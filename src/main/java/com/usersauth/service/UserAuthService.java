package com.usersauth.service;

import com.usersauth.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserAuthService {

    private static final Map<String, User> usersMap = new HashMap<>();

    static {
        buildUsersData();
    }

    private static void buildUsersData() {
        User another = new User("another", "the loose");
        usersMap.put("another", another);
    }

    public boolean login(String username, String password) {
        User user = usersMap.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
