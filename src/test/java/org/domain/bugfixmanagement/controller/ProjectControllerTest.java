package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.controller.projects.ProjectsController;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.service.ProjectService;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private final ProjectService projectService = Mockito.mock(ProjectService.class);

    @BeforeEach
    public void setup(){
        ProjectsController projectsController = new ProjectsController(projectService);
        mockMvc = MockMvcBuilders.standaloneSetup(projectsController)
                .setHandlerExceptionResolvers(exceptionResolver())
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void shouldReturnAllProjects() throws Exception {
        Project project = new Project(1L, new User(), "First project", "First project description", "Please read instructions");

        List<Project> allProjects = Arrays.asList(project);
        given(projectService.getAllProjects()).willReturn(allProjects);
        this.mockMvc.perform(get("/projects")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(project.getTitle())));
        verify(projectService, times(1)).getAllProjects();
    }

    @Test
    public void shouldReturnProjectById() throws Exception {
        Project project = new Project(1L, new User(), "FirstProject", "The first project", "status");

        given(projectService.getProjectById(any())).willReturn(project);
        this.mockMvc.perform(get(URI.create("/get-project?project_id=1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(projectService,times(1)).getProjectById(any());
    }

    @Test
    public void shouldDeleteProjectById() throws Exception {
        Project project = new Project();
        project.setProjectId(1L);
        project.setTitle("First project");

        doNothing().when(projectService).deleteProject(project.getProjectId());

        this.mockMvc.perform(delete("/delete-project?project_id=" + project.getProjectId().toString())
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
