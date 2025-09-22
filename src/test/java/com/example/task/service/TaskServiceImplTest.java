package com.example.task.service;

import com.example.task.model.Task;
import com.example.task.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class TaskServiceImplTest {
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskServiceImpl();
    }

    @Test
    public void testCreateTask() {
        Task task = new Task();
        task.setId(1L);
        task.setUserId(11L);
        task.setTitle("Work");
        task.setDescription("work");
        task.setCompleted(true);
        task.setDeleted(false);
        task.setCreationDate(LocalDate.of(2025,8,9).atStartOfDay());
        task.setTargetDate(LocalDate.of(2035,8,10).atStartOfDay());
        taskService.createTask(task);

        assertNotNull(task);
        assertEquals(1L, task.getId());
        assertEquals(11L, task.getUserId());
        assertEquals("Work", task.getTitle());
        assertEquals("work", task.getDescription());
        assertTrue(task.isCompleted());
        assertFalse(task.isDeleted());
        assertEquals(LocalDate.of(2035,8,10).atStartOfDay(), task.getTargetDate());
        assertEquals(LocalDate.of(2025,8,9).atStartOfDay(), task.getCreationDate());

    }

    @Test
    public void testDeleteTask() {

        Task task1 = new Task();
        task1.setId(1L);
        task1.setUserId(11L);
        task1.setTitle("Work");
        task1.setDescription("work");
        task1.setCompleted(true);
        task1.setDeleted(false);
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(11L);
        task2.setTitle("Study");
        task2.setDescription("study");
        task2.setCompleted(false);
        task2.setDeleted(false);
        taskService.createTask(task2);

        assertFalse(task2.isDeleted());
        taskService.deleteTask(task2.getId());

        List<Task> tasks = taskService.getAllTasks(11L);
        assertFalse(tasks.stream().anyMatch(task -> task.getId().equals(task2.getId())));
        assertTrue(tasks.stream().anyMatch(task -> task.getId().equals(task1.getId())));
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
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(11L);
        task2.setTitle("Study");
        task2.setDescription("study");
        task2.setCompleted(false);
        task2.setDeleted(false);
        taskService.createTask(task2);

        List<Task> tasks = taskService.getAllTasks(11L);
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertEquals("Work", tasks.get(0).getTitle());
        assertEquals("study", tasks.get(1).getDescription());
        assertTrue( tasks.get(0).isCompleted());
        assertFalse( tasks.get(1).isDeleted());
        assertTrue( tasks.get(0).isCompleted());
        assertFalse( tasks.get(0).isDeleted());
    }

    @Test
    public void testGetPendingTasks() {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setUserId(11L);
        task1.setTitle("Work");
        task1.setCompleted(true);
        task1.setDeleted(false);
        taskService.createTask(task1);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(11L);
        task2.setTitle("Study");
        task2.setCompleted(false);
        task2.setDeleted(false);
        taskService.createTask(task2);

        Task task3 = new Task();
        task3.setId(3L);
        task3.setUserId(11L);
        task3.setTitle("Sleep");
        task3.setCompleted(false);
        task3.setDeleted(true);
        taskService.createTask(task3);
        List<Task> tasks = taskService.getPendingTasks(11L);

        assertNotNull(tasks);
        assertEquals(1, tasks.size());
        assertFalse(tasks.contains(task1));
        assertTrue(tasks.contains(task2));
        assertFalse(tasks.contains(task3));
    }

}