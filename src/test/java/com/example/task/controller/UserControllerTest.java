package com.example.task.controller;
import com.example.task.model.User;
import com.example.task.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService)).build();
    }


    @Test
    void testRegistrationUser() throws Exception {
        User user = new User();
        user.setName("ann");
        user.setPassword("cat555");

        when(userService.registration(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/users/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("ann"));
    }

    @Test
    void testLoginUser() throws Exception {
        User user = new User();
        user.setName("kate");
        user.setPassword("dog333");

        when(userService.login("kate", "dog333")).thenReturn(user);

        mockMvc.perform(get("/users/login")
                        .param("username", "kate")
                        .param("password", "dog333"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("kate"));
    }

    @Test
    void testUnauthorizedUser() throws Exception {
        User user = new User();
        user.setName("tanya");
        user.setPassword("meow355");
        when(userService.login("tanya", "meow355")).thenReturn(null);

        mockMvc.perform(get("/users/login")
                        .param("username", "tanya")
                        .param("password", "meow355"))
                .andExpect(status().isUnauthorized());
    }
}
