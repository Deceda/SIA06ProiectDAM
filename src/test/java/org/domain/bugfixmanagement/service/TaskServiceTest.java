package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.Task;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.TaskRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
}
