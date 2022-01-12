package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.TaskRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class TaskServiceTest {
    private final TaskRepository taskRepository = Mockito.mock(TaskRepository.class);

    private final TaskService taskService = new TaskService(taskRepository);

    @Test
    public void test1(){
        given(taskRepository.findAll()).willReturn(Collections.emptyList());
        List<Task> result = taskService.getAllTasks();
        assertThat(result).isEmpty();
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void test2(){
        given(taskRepository.findAll()).willReturn(List.of
                (new Task(1L, new Project(), new User(), "FirstTask", "the first task", "created", "20 May"),
                        new Task(2L, new Project(), new User(), "SecondTask", "the second task", "opened", "25 July"),
                        new Task(3L, new Project(), new User(), "ThirdTask", "the third task", "solved", "13 August")));
        List<Task> result = taskService.getAllTasks();
        assertThat(result).hasSize(3);
        verify(taskRepository, times(1)).findAll();
    }

    @Test
    public void shouldReturnTaskById(){
        Task task = new Task();
        task.setTaskId(1L);

        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));

        Task expected = taskService.getTaskById(task.getTaskId());
        assertThat(expected).isSameAs(task);
        verify(taskRepository).findById(task.getTaskId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTaskDoesntExist() {
        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("First task");

        given(taskRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        taskService.getTaskById(task.getTaskId());
    }

    @Test
    public void shouldReturnBugByProjectId(){
        Project project = new Project(1L, new User(), "FirstProject", "The first project", "status");
        Task task1 = new Task(1L, project, new User(), "FirstTask", "the first task", "created", "20 May 2021");
        Task task2 = new Task(2L, project, new User(), "SecondTask", "the second task", "opened", "25 July 2021");

        when(taskRepository.getTasksByProject(project.getProjectId())).thenReturn(List.of(task1, task2));

        List<Task> result = taskService.getTaskByProjectId(project.getProjectId());
        assertThat(result).hasSize(2);
        verify(taskRepository).getTasksByProject(project.getProjectId());
    }

    @Test
    public void shouldDeleteTaskById(){
        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("First task");

        when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));

        taskService.deleteTask(task.getTaskId());
        verify(taskRepository).deleteById(task.getTaskId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenTaskDoesntExistToBeDeleted() {
        Task task = new Task();
        task.setTaskId(1L);
        task.setTitle("First task");

        given(taskRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        taskService.deleteTask(task.getTaskId());
    }

    @Test
    public void shouldReturnAddedBug() {
        Task task = new Task();
        task.setTitle("First task");

        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Task createdTask = taskService.addTask(any(Long.class), any(Long.class), any(String.class), any(String.class), any(String.class));

        assertThat(createdTask.getTitle()).isSameAs(task.getTitle());
        verify(taskRepository).save(task);
    }
}
