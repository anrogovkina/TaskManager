package com.example.task.controller;

import com.example.task.model.Notification;
import com.example.task.service.NotificationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public ResponseEntity<Notification> createNotification(@RequestBody Notification notification) {
        try{
            return ResponseEntity.ok(notificationService.createNotification(notification));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Notification>> getAllNotification(@PathVariable Long userId) {
        try {
            return ResponseEntity.ok(notificationService.getAllNotifications(userId));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}/pending")
    public ResponseEntity<List<Notification>> getPendingNotification(@PathVariable Long userId) {
        try{
            return ResponseEntity.ok(notificationService.getPendingNotifications(userId));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}