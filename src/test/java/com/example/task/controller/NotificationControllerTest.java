package com.example.task.controller;
import com.example.task.model.Notification;
import com.example.task.service.NotificationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class NotificationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private NotificationService notificationService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new NotificationController(notificationService)).build();
    }


    @Test
    void testCreateNotification() throws Exception {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setUserId(11L);
        notification.setMessage("Notification 1");

        when(notificationService.createNotification(any())).thenReturn(notification);

        mockMvc.perform(
                        post("/notifications")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(notification)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.userId").value(11))
                .andExpect(jsonPath("$.message").value("Notification 1"));

    }

    @Test
    void testGetAllNotifications() throws Exception {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(11L);
        notification1.setMessage("Notification 1");

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(11L);
        notification2.setMessage("Notification 2");

        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getAllNotifications(11L)).thenReturn(notifications);

        mockMvc.perform(
                        get("/notifications/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$.[0].userId").value(11L))
                .andExpect(jsonPath("$.[0].message").value("Notification 1"))
                .andExpect(jsonPath("$.[1].id").value(2L))
                .andExpect(jsonPath("$.[1].userId").value(11L))
                .andExpect(jsonPath("$.[1].message").value("Notification 2"));
    }

    @Test
    void testGetPendingNotifications() throws Exception {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(11L);
        notification1.setMessage("Notification 1");
        notification1.setRead(false);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(11L);
        notification2.setMessage("Notification 2");
        notification2.setRead(false);

        List<Notification> notifications = Arrays.asList(notification1, notification2);

        when(notificationService.getPendingNotifications(11L)).thenReturn(notifications);
        mockMvc.perform(
                        get("/notifications/11/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(11L))
                .andExpect(jsonPath("$[0].read").value(false))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].userId").value(11L))
                .andExpect(jsonPath("$[1].read").value(false));


    }
}
