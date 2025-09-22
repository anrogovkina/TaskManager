package com.example.task.service;

import com.example.task.model.User;
import com.example.task.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceImplTest {
    private UserService userService;

    @BeforeEach
    void setUp() {

        userService = new UserServiceImpl();
    }

    @Test
    void testUserRegistration() {
        User user = new User();
        user.setName("ann");
        user.setPassword("cat555");

        User reg = userService.registration(user);

        assertNotNull(reg);
        assertEquals("ann", reg.getName());
    }

    @Test
    void testRegistrationDuplicateUsers() {
        User user1 = new User();
        user1.setName("kate");
        user1.setPassword("cat123");

        userService.registration(user1);

        User user2 = new User();
        user2.setName("kate");
        user2.setPassword("dog123");

        assertThrows(IllegalArgumentException.class,
                () -> userService.registration(user2));
    }

    @Test
    void testLogin() {
        User user = new User();
        user.setName("emily");
        user.setPassword("meow333");

        userService.registration(user);

        User log = userService.login("emily", "meow333");
        assertNotNull(log);
        assertEquals("emily", log.getName());
    }

    @Test
    void testLoginWrongPassword() {
        User user = new User();
        user.setName("tanya");
        user.setPassword("meow123");

        userService.registration(user);

        User log= userService.login("tanya", "cat123");
        assertNull(log);
    }

    @Test
    void testLoginUserNotFound() {
        User log = userService.login("mary", "cat111");
        assertNull(log);
    }
}