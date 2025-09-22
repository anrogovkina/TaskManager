package com.example.task.service.impl;

import com.example.task.model.User;
import com.example.task.service.UserService;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private final Map<String, User> users = new HashMap<>();

    @Override
    public User registration(User user) {
        if (users.containsKey(user.getName())) {
            throw new IllegalArgumentException("User with this name already exists!");
        }
        users.put(user.getName(), user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}