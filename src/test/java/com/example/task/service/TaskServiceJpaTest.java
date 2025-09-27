package com.example.task.service;

import com.example.task.model.Task;
import com.example.task.repository.TaskRepository;
import com.example.task.service.impl.TaskServiceJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceJpaTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskServiceJpa taskService;

    private Task testTask;

    @BeforeEach
    public void setUp() {
        testTask = new Task();
        testTask.setId(1L);
        testTask.setUserId(11L);
        testTask.setTitle("Work");
        testTask.setDescription("work");
        testTask.setCompleted(true);
        testTask.setDeleted(false);
        testTask.setCreationDate(LocalDate.of(2025,8,9).atStartOfDay());
        testTask.setTargetDate(LocalDate.of(2035,8,10).atStartOfDay());
    }

    @Test
    public void testCreateTask() {
        when(taskRepository.save(any(Task.class))).thenReturn(testTask);

        Task result = taskService.createTask(testTask);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(11L, result.getUserId());
        assertEquals("Work", result.getTitle());
        assertEquals("work", result.getDescription());
        assertTrue(result.isCompleted());
        assertFalse(result.isDeleted());
        assertEquals(LocalDate.of(2035,8,10).atStartOfDay(), result.getTargetDate());
        assertEquals(LocalDate.of(2025,8,9).atStartOfDay(), result.getCreationDate());

        verify(taskRepository, times(1)).save(testTask);
    }

    @Test
    public void testDeleteTask() {
        Task taskToDelete = new Task();
        taskToDelete.setId(2L);
        taskToDelete.setUserId(11L);
        taskToDelete.setDeleted(false);

        when(taskRepository.findById(2L)).thenReturn(Optional.of(taskToDelete));
        when(taskRepository.save(any(Task.class))).thenReturn(taskToDelete);

        taskService.deleteTask(2L);

        assertTrue(taskToDelete.isDeleted());
        verify(taskRepository, times(1)).findById(2L);
        verify(taskRepository, times(1)).save(taskToDelete);
    }

    @Test
    public void testDeleteTask_NotFound() {
        when(taskRepository.findById(3L)).thenReturn(Optional.empty());

        taskService.deleteTask(3L);

        verify(taskRepository, times(1)).findById(3L);
        verify(taskRepository, never()).save(any(Task.class));
    }

    @Test
    public void testGetAllTasks() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setUserId(11L);
        task1.setTitle("Work");
        task1.setDescription("work");
        task1.setCompleted(true);
        task1.setDeleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(11L);
        task2.setTitle("Study");
        task2.setDescription("study");
        task2.setCompleted(false);
        task2.setDeleted(false);

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskRepository.findAllByUserIdAndDeletedFalse(11L)).thenReturn(tasks);

        List<Task> result = taskService.getAllTasks(11L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Work", result.get(0).getTitle());
        assertEquals("study", result.get(1).getDescription());
        assertTrue(result.get(0).isCompleted());
        assertFalse(result.get(1).isDeleted());

        verify(taskRepository, times(1)).findAllByUserIdAndDeletedFalse(11L);
    }

    @Test
    public void testGetPendingTasks() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setUserId(11L);
        task1.setTitle("Work");
        task1.setCompleted(true);
        task1.setDeleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(11L);
        task2.setTitle("Study");
        task2.setCompleted(false);
        task2.setDeleted(false);

        Task task3 = new Task();
        task3.setId(3L);
        task3.setUserId(11L);
        task3.setTitle("Sleep");
        task3.setCompleted(false);
        task3.setDeleted(true);

        List<Task> pendingTasks = Arrays.asList(task2);

        when(taskRepository.findAllByUserIdAndDeletedFalseAndCompletedFalse(11L)).thenReturn(pendingTasks);

        List<Task> result = taskService.getPendingTasks(11L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(task2));
        assertFalse(result.contains(task1));
        assertFalse(result.contains(task3));

        verify(taskRepository, times(1)).findAllByUserIdAndDeletedFalseAndCompletedFalse(11L);
    }

}