package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProjectServiceTest {
    private final ProjectRepository projectRepository = Mockito.mock(ProjectRepository.class);

    private final ProjectService projectService = new ProjectService(projectRepository);

    @Test
    public void test1(){
        given(projectRepository.findAll()).willReturn(Collections.emptyList());
        List<Project> result = projectService.getAllProjects();
        assertThat(result).isEmpty();
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    public void test2(){
        given(projectRepository.findAll()).willReturn(List.of
                (new Project(1L, new User(), "FirstProject", "The first project", "status"),
                        new Project(2L, new User(), "SecondProject", "The second project", "status"),
                        new Project(3L, new User(), "ThirdProject", "The third project", "status")));
        List<Project> result = projectService.getAllProjects();
        assertThat(result).hasSize(3);
        verify(projectRepository, times(1)).findAll();
    }
}
