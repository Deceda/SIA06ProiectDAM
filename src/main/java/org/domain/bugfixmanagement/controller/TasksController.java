package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.service.TaskService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TasksController {

    private final TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public List<Task> tasks() {
        return taskService.getAllTasks();
    }
}
