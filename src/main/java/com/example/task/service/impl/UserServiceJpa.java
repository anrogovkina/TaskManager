package com.example.task.service.impl;

import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.service.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile({"h2","postgres"})
public class UserServiceJpa implements UserService {
    private final UserRepository userRepository;
    public UserServiceJpa(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User registration(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        return userRepository.findByName(username)
                .filter(u -> u.getPassword().equals(password)).orElse(null);
    }
}
