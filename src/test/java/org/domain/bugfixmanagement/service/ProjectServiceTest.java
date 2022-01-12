package org.domain.bugfixmanagement.service;

import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.repository.ProjectRepository;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

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

    @Test
    public void shouldReturnProjectById(){
        Project project = new Project();
        project.setProjectId(1L);

        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));

        Project expected = projectService.getProjectById(project.getProjectId());
        assertThat(expected).isSameAs(project);
        verify(projectRepository).findById(project.getProjectId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenProjectDoesntExist() {
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("First project");

        given(projectRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        projectService.getProjectById(project.getProjectId());
    }

    @Test
    public void shouldDeleteProjectById(){
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("First project");

        when(projectRepository.findById(project.getProjectId())).thenReturn(Optional.of(project));

        projectService.deleteProject(project.getProjectId());
        verify(projectRepository).deleteById(project.getProjectId());
    }

    @Test(expected=IllegalArgumentException.class)
    public void shouldThrowExceptionWhenProjectDoesntExistToBeDeleted() {
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("First project");

        given(projectRepository.findById(anyLong())).willReturn(Optional.ofNullable(null));
        projectService.deleteProject(project.getProjectId());
    }

    @Test
    public void shouldReturnAddedProject() {
        Project project = new Project();
        project.setTitle("First project");

        when(projectRepository.save(any(Project.class))).thenReturn(project);

        Project createdProject = projectService.addProject(any(Long.class), any(String.class), any(String.class), any(String.class));

        assertThat(createdProject.getTitle()).isSameAs(project.getTitle());
        verify(projectRepository).save(project);
    }
}
