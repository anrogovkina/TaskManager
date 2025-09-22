package com.example.task.service;
import com.example.task.model.User;


public interface UserService {
    User registration(User user);
    User login(String username, String password);
}
