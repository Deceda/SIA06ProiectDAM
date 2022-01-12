package org.domain.bugfixmanagement.service;

import lombok.AllArgsConstructor;
import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Task not found"));
    }

    public List<Task> getTaskByProjectId(Long id){return taskRepository.getTasksByProject(id);}

    public void deleteTask(Long taskId){
        taskRepository.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        taskRepository.deleteById(taskId);
    }

    public Task addTask(long projectId, long userId, String title, String task_status, String task_description){
        if (title.length() < 50 && task_status.length() < 20 && task_description.length() < 1000){
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            Date date = new Date();
            String current = formatter.format(date);

            taskRepository.insert_task(projectId, userId, title, task_status, task_description, current);
        }
        return null;
    }
}
