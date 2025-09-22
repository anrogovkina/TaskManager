package com.example.task.service;

import com.example.task.model.Notification;
import com.example.task.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class NotificationServiceImplTest {

    private NotificationServiceImpl notificationService;

    @BeforeEach
    void setUp() {
        notificationService = new NotificationServiceImpl();
    }

    @Test
    void testCreateNotification() {
        Notification notification = new Notification();
        notification.setId(1L);
        notification.setUserId(11L);
        notification.setMessage("Notification 1");
        notification.setRead(true);
        notificationService.createNotification(notification);

        assertNotNull(notification);
        assertEquals(1L, notification.getId());
        assertEquals(11L, notification.getUserId());
        assertEquals("Notification 1", notification.getMessage());
        assertTrue(notification.isRead());
    }

    @Test
    void testGetAllNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(22L);
        notification1.setMessage("Notification 1");
        notification1.setRead(false);
        notificationService.createNotification(notification1);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(22L);
        notification2.setMessage("Notification 2");
        notification2.setRead(true);
        notificationService.createNotification(notification2);
        List<Notification> notifications = notificationService.getAllNotifications(22L);

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        assertTrue(notifications.contains(notification1));
        assertTrue(notifications.contains(notification2));
        assertEquals("Notification 1", notifications.get(0).getMessage());
        assertTrue( notifications.get(1).isRead());
    }
    @Test
    void testGetPendingNotifications() {
        Notification notification1 = new Notification();
        notification1.setId(1L);
        notification1.setUserId(33L);
        notification1.setMessage("Notification 1");
        notification1.setRead(false);
        notificationService.createNotification(notification1);

        Notification notification2 = new Notification();
        notification2.setId(2L);
        notification2.setUserId(33L);
        notification2.setMessage("Notification 2");
        notification2.setRead(true);
        notificationService.createNotification(notification2);

        Notification notification3 = new Notification();
        notification3.setId(3L);
        notification3.setUserId(33L);
        notification3.setMessage("Notification 3");
        notification3.setRead(false);
        notificationService.createNotification(notification3);

        List<Notification> notifications = notificationService.getPendingNotifications(33L);

        assertNotNull(notifications);
        assertEquals(2, notifications.size());
        assertTrue(notifications.contains(notification1));
        assertFalse(notifications.contains(notification2));
        assertTrue(notifications.contains(notification3));
        assertFalse( notifications.get(0).isRead());
        assertFalse( notifications.get(1).isRead());

    }
}