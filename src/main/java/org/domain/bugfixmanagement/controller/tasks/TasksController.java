package org.domain.bugfixmanagement.controller.tasks;

import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class TasksController {

    private final TaskService taskService;

    @Autowired
    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> tasks() {
        return ResponseEntity.ok().body(taskService.getAllTasks());
    }

    @GetMapping("/get-task")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Task> getBugById(@RequestParam(name = "task_id") Long id){
        return ResponseEntity.ok().body(taskService.getTaskById(id));
    }

    @GetMapping("/get-task-by-projectId")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Task>> getTaskByProjectId(@RequestParam(name = "project_id") Long id){
        return ResponseEntity.ok().body(taskService.getTaskByProjectId(id));
    }

    @PostMapping("/add-task")
    public void addTask(@RequestBody TaskRequest request) {
        if (request.validateParameters()) {
            taskService.addTask(request.getProject_id(), request.getUser_id(), request.getTitle(), request.getTask_status(), request.getTask_description());
        }
    }

    @DeleteMapping("/delete-task")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@RequestParam(name = "task_id") Long id){
        taskService.deleteTask(id);
    }
}
