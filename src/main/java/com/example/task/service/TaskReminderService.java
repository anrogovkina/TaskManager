package com.example.task.service;

import com.example.task.model.Task;
import com.example.task.model.Notification;
import com.example.task.repository.TaskRepository;
import com.example.task.repository.NotificationRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskReminderService {

    private final TaskRepository taskRepository;
    private final NotificationRepository notificationRepository;

    public TaskReminderService(TaskRepository taskRepository, NotificationRepository notificationRepository) {
        this.taskRepository = taskRepository;
        this.notificationRepository = notificationRepository;
    }

    @Scheduled(fixedRate = 18000000)
    public void checkOverdueTasks() {
        List<Task> overdue = taskRepository.findByTargetDateBeforeAndCompletedFalseAndDeletedFalse(
                LocalDateTime.now());
        for (Task task : overdue) {
        createReminder(task,"Task " + task.getTitle() + " is overdue! Deadline was at " + task.getTargetDate());
        }
    }

    @Scheduled(cron ="0 0 7 * * ?")
    public void checkTasksForToday(){
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        List<Task> dueToday = taskRepository.findTasksDueToday(startOfDay, endOfDay);
        for (Task task : dueToday) {
            createReminder(task,"Deadline for task " + task.getTitle() + " is today!");
        }

    }
    @Async
    public void createReminder(Task task,String message) {
        Notification reminder = new Notification();
        reminder.setUserId(task.getUserId());
        reminder.setMessage(message);
        reminder.setRead(false);
        notificationRepository.save(reminder);
    }
}
