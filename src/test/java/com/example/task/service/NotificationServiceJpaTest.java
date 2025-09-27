package com.example.task.service;

import com.example.task.model.Notification;
import com.example.task.repository.NotificationRepository;
import com.example.task.service.impl.NotificationServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceJpaTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceJpa notificationService;

    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new Notification();
        testNotification.setId(1L);
        testNotification.setUserId(11L);
        testNotification.setMessage("Notification 1");
        testNotification.setRead(true);
    }

    @Test
    void testCreateNotification() {
        when(notificationRepository.save(any(Notification.class))).thenReturn(testNotification);

        Notification result = notificationService.createNotification(testNotification);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(11L, result.getUserId());
        assertEquals("Notification 1", result.getMessage());
        assertTrue(result.isRead());

        verify(notificationRepository, times(1)).save(testNotification);
    }

    @Test
    void testGetAllNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(22L);
        notification1.setMessage("Notification 1");
        notification1.setRead(false);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(22L);
        notification2.setMessage("Notification 2");
        notification2.setRead(true);

        List<Notification> notifications = Arrays.asList(notification1, notification2);
        when(notificationRepository.findAllByUserId(22L)).thenReturn(notifications);
        List<Notification> result = notificationService.getAllNotifications(22L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(notification1));
        assertTrue(result.contains(notification2));
        assertEquals("Notification 1", result.get(0).getMessage());
        assertTrue(result.get(1).isRead());

        verify(notificationRepository, times(1)).findAllByUserId(22L);
    }

    @Test
    void testGetPendingNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(33L);
        notification1.setMessage("Notification 1");
        notification1.setRead(false);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(33L);
        notification2.setMessage("Notification 2");
        notification2.setRead(true);

        Notification notification3 = new Notification();
        notification3.setId(3L);
        notification3.setUserId(33L);
        notification3.setMessage("Notification 3");
        notification3.setRead(false);

        List<Notification> pendingNotifications = Arrays.asList(notification1, notification3);
        when(notificationRepository.findAllByUserIdAndReadFalse(33L)).thenReturn(pendingNotifications);
        List<Notification> result = notificationService.getPendingNotifications(33L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(result.contains(notification1));
        assertFalse(result.contains(notification2));
        assertTrue(result.contains(notification3));
        assertFalse(result.get(0).isRead());
        assertFalse(result.get(1).isRead());

        verify(notificationRepository, times(1)).findAllByUserIdAndReadFalse(33L);
    }


    @Test
    void testGetAllNotifications_EmptyList() {
        when(notificationRepository.findAllByUserId(3L)).thenReturn(Arrays.asList());

        List<Notification> result = notificationService.getAllNotifications(3L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(notificationRepository, times(1)).findAllByUserId(3L);
    }

    @Test
    void testGetPendingNotifications_EmptyList() {
        when(notificationRepository.findAllByUserIdAndReadFalse(3L)).thenReturn(Arrays.asList());

        List<Notification> result = notificationService.getPendingNotifications(3L);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(notificationRepository, times(1)).findAllByUserIdAndReadFalse(3L);
    }
}