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
        User berit = new User("berit", "123456");
        User call = new User("call", "password");

        usersMap.put("another", another);
        usersMap.put("berit", berit);
        usersMap.put("call", call);
    }

    public boolean login(String username, String password) {
        User user = usersMap.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
