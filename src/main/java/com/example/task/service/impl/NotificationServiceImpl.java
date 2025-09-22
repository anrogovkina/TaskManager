package com.example.task.service.impl;
import com.example.task.model.Notification;
import com.example.task.service.NotificationService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final Map<Long, Notification> notifications = new HashMap<>();

    @Override
    public Notification createNotification(Notification notification) {
        notifications.put(notification.getId(), notification);
        return notification;
    }

    @Override
    public List<Notification> getAllNotifications(Long userId) {
        return notifications.values().stream()
                .filter(n -> n.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Notification> getPendingNotifications(Long userId) {
        return notifications.values().stream()
                .filter(n -> n.getUserId().equals(userId) && !n.isRead())
                .collect(Collectors.toList());
    }
}
