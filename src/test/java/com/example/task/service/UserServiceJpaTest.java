package com.example.task.service;

import com.example.task.model.User;
import com.example.task.repository.UserRepository;
import com.example.task.service.impl.UserServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceJpaTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceJpa userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1L);
        testUser.setName("testuser");
        testUser.setPassword("test");
    }

    @Test
    void testUserRegistration_Success() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        User registeredUser = userService.registration(testUser);

        assertNotNull(registeredUser);
        assertEquals("testuser", registeredUser.getName());
        assertEquals(1L, registeredUser.getId());

        verify(userRepository, times(1)).save(testUser);
    }

    @Test
    void testLogin_Success() {
        when(userRepository.findByName("emily")).thenReturn(Optional.of(testUser));

        User loggedInUser = userService.login("emily", "test");

        assertNotNull(loggedInUser);
        assertEquals("testuser", loggedInUser.getName());
        verify(userRepository, times(1)).findByName("emily");
    }

    @Test
    void testLogin_WrongPassword() {
        when(userRepository.findByName("tanya")).thenReturn(Optional.of(testUser));

        User loggedInUser = userService.login("tanya", "wrong");

        assertNull(loggedInUser);
        verify(userRepository, times(1)).findByName("tanya");
    }

    @Test
    void testLogin_UserNotFound() {
        when(userRepository.findByName("mary")).thenReturn(Optional.empty());

        User loggedInUser = userService.login("mary", "cat111");

        assertNull(loggedInUser);
        verify(userRepository, times(1)).findByName("mary");
    }
}