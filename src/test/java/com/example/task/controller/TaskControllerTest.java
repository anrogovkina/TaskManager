package com.example.task.controller;

import com.example.task.model.Task;
import com.example.task.service.TaskService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateTask() throws Exception {
        Task task = new Task();
        task.setId(1L);
        task.setUserId(11L);
        task.setTitle("Title");

        when(taskService.createTask(any(Task.class))).thenReturn(task);
        mockMvc.perform(
                        post("/tasks")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.userId").value(11L))
                .andExpect(jsonPath("$.title").value("Title"));

    }

    @Test
    void testDeleteTask() throws Exception {
        doNothing().when(taskService).deleteTask(1L);

        mockMvc.perform(delete("/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testGetAllTasks() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setUserId(11L);
        task1.setTitle("Task 1");

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(11L);
        task2.setTitle("Task 2");

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getAllTasks(11L)).thenReturn(tasks);
        mockMvc.perform(get("/tasks/11"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$.[0].userId").value(11L))
                .andExpect(jsonPath("$.[0].title").value("Task 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$.[1].userId").value(11L))
                .andExpect(jsonPath("$.[1].title").value("Task 2"));


    }

    @Test
    void testGetPendingTasks() throws Exception {
        Task task1 = new Task();
        task1.setId(1L);
        task1.setUserId(22L);
        task1.setTitle("Task 1");
        task1.setCompleted(false);

        Task task2 = new Task();
        task2.setId(2L);
        task2.setUserId(22L);
        task2.setTitle("Task 2");
        task2.setCompleted(false);

        List<Task> tasks = Arrays.asList(task1, task2);

        when(taskService.getPendingTasks(22L)).thenReturn(tasks);
        mockMvc.perform(get("/tasks/22/pending"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].userId").value(22L))
                .andExpect(jsonPath("$[0].completed").value(false))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].userId").value(22L))
                .andExpect(jsonPath("$[1].completed").value(false));

    }
}

