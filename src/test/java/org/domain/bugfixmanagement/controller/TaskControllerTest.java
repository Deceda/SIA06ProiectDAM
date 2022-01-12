package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.controller.tasks.TasksController;
import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private final TaskService taskService = Mockito.mock(TaskService.class);

    @BeforeEach
    public void setup(){
        TasksController tasksController = new TasksController(taskService);
        mockMvc = MockMvcBuilders.standaloneSetup(tasksController)
                .setHandlerExceptionResolvers(exceptionResolver())
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void shouldReturnAllTasks() throws Exception {
        Task task = new Task(1L, new Project(), new User(), "First task", "Task title", "Created", "Test task");

        List<Task> allTasks = Arrays.asList(task);
        given(taskService.getAllTasks()).willReturn(allTasks);
        this.mockMvc.perform(get("/tasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(task.getTitle())));
        verify(taskService, times(1)).getAllTasks();
    }

    @Test
    public void shouldReturnTaskById() throws Exception {
        Task task = new Task(1L, new Project(), new User(), "First task", "Task title", "Created", "Test task");
        given(taskService.getTaskById(any())).willReturn(task);
        this.mockMvc.perform(get(URI.create("/get-task?task_id=1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(taskService, times(1)).getTaskById(any());
    }

    @Test
    public void shouldReturnTaskByProjectId() throws Exception {
        Task task1 = new Task(1L, new Project(), new User(), "FirstTask", "the first task", "created", "20 May 2021");
        Task task2 = new Task(2L, new Project(), new User(), "SecondTask", "the second task", "opened", "25 July 2021");
        Task task3 = new Task(3L, new Project(), new User(), "ThirdTask", "the third task", "solved", "13 August 2021");

        given(taskService.getTaskByProjectId(any())).willReturn(List.of(task1, task2, task3));
        this.mockMvc.perform(get(URI.create("/get-task-by-projectId?project_id=1"))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
        verify(taskService, times(1)).getTaskByProjectId(1L);
    }

    @Test
    public void shouldDeleteTaskById() throws Exception{
        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("First task");

        doNothing().when(taskService).deleteTask(task.getTaskId());

        this.mockMvc.perform(delete("/delete-task?task_id=" + task.getTaskId().toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    //helper functions
    private HandlerExceptionResolver exceptionResolver() {
        SimpleMappingExceptionResolver exceptionResolver = new SimpleMappingExceptionResolver();

        Properties exceptionMappings = new Properties();

        exceptionMappings.put("net.petrikainulainen.spring.testmvc.todo.exception.TodoNotFoundException", "error/404");
        exceptionMappings.put("java.lang.Exception", "error/error");
        exceptionMappings.put("java.lang.RuntimeException", "error/error");

        exceptionResolver.setExceptionMappings(exceptionMappings);

        Properties statusCodes = new Properties();

        statusCodes.put("error/404", "404");
        statusCodes.put("error/error", "500");

        exceptionResolver.setStatusCodes(statusCodes);

        return exceptionResolver;
    }

    private ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

        viewResolver.setViewClass(JstlView.class);
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        return viewResolver;
    }
}
