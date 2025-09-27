package com.example.task.service;

import com.example.task.model.Notification;
import com.example.task.model.Task;
import com.example.task.repository.NotificationRepository;
import com.example.task.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskReminderServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private TaskReminderService taskReminderService;

    @Test
    public void testCheckOverdueTasks(){
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Overdue");
        task.setUserId(11L);
        task.setTargetDate(LocalDateTime.now().minusDays(1));
        task.setDeleted(false);
        task.setCompleted(false);

        when(taskRepository.
                findByTargetDateBeforeAndCompletedFalseAndDeletedFalse(any(LocalDateTime.class)))
                .thenReturn(List.of(task));
        taskReminderService.checkOverdueTasks();
        verify(notificationRepository,times(1)).save(any(Notification.class));
    }

    @Test
    public void testCheckTasksForToday(){
        Task task = new Task();
        task.setId(1L);
        task.setTitle("Due");
        task.setUserId(11L);
        task.setTargetDate(LocalDateTime.now());

        when(taskRepository.findTasksDueToday(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(List.of(task));

        taskReminderService.checkTasksForToday();

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }
    }


