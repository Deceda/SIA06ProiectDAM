package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.controller.bugs.BugsController;
import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.service.BugService;
import org.domain.bugfixmanagement.util.JsonUtil;
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
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BugControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private final BugService bugService = Mockito.mock(BugService.class);

    private static ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    public void setup(){
        BugsController bugsController = new BugsController(bugService);
        mockMvc = MockMvcBuilders.standaloneSetup(bugsController)
                .setHandlerExceptionResolvers(exceptionResolver())
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void shouldReturnAllBugs() throws Exception {
        Bug bug = new Bug(1L, new Project(), new User(), "titlu", "descriere", "status", "creat");

        List<Bug> allBugs = Arrays.asList(bug);
        given(bugService.getAllBugs()).willReturn(allBugs);
        this.mockMvc.perform(get("/bugs").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].title", is(bug.getTitle())));
        verify(bugService, times(1)).getAllBugs();
    }

    @Test
    public void shouldReturnBugById() throws Exception {
        given(bugService.getBugById(any())).willReturn((new Bug(1L, new Project(), new User(), "titlu", "descriere", "status", "creat")));
        this.mockMvc.perform(get(URI.create("/get-bug?bug_id=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(bugService, times(1)).getBugById(any());
    }

    @Test
    public void shouldReturnBugByProjectId() throws Exception {
        given(bugService.getBugByProjectId(1L)).willReturn(List.of
                (new Bug(1L, new Project(1L, new User(), "FirstProject", "The first project", "status"), new User(), "bug1", "descriere1", "status1", "01.01.2022"),
                new Bug(1L, new Project(1L, new User(), "FirstProject", "The first project", "status"), new User(), "bug2", "descriere2", "status2", "01.02.2022"),
                new Bug(2L, new Project(1L, new User(), "FirstProject", "The first project", "status"), new User(), "bug3", "descriere3", "status3", "01.03.2022")));
        this.mockMvc.perform(get(URI.create("/get-bug-by-projectId?project_id=1")).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(bugService, times(1)).getBugByProjectId(1L);
    }

    @Test
    public void shouldAddNewBug() throws Exception{
        Bug new_bug = new Bug(1L, new Project(), new User(), "titlu", "descriere", "status", "creat");
        given(bugService.addBug(any(Long.class), any(Long.class), any(String.class), any(String.class), any(String.class) )).willReturn(new_bug);

        this.mockMvc.perform(post("/add-bug")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(new_bug)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is(new_bug.getTitle())));
        verify(bugService, times(1)).addBug(any(Long.class), any(Long.class), any(String.class), any(String.class), any(String.class));
    }

    @Test
    public void testPutExample() throws Exception {
        Bug bug = new Bug();
        bug.setBugId(2L);
        bug.setBug_status("updated");
        Mockito.when(bugService.updateBugv2(any())).thenReturn(bug);
        String json = mapper.writeValueAsString(bug);
        this.mockMvc.perform(put("/putMapping").contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
                        .andDo(print());
//                      .andExpect(jsonPath("$[0].bug_id", equalTo(2)))
//                      .andExpect(jsonPath("$.bug_status", is("updated")));
        verify(bugService, times(1)).updateBugv2(any());
    }

    @Test
    public void shouldUpdateBugStatus() throws Exception {
        Bug bug = new Bug(1L, new Project(), new User(), "bug1", "descriere1", "new", "01.01.2022");
        given(bugService.updateBug(1L, "updated")).willReturn(bug);
        String json = mapper.writeValueAsString(bug);
        this.mockMvc.perform(put(URI.create("/update-bug-status-by-id?bug_id=1"))
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void shouldDeleteBugById() throws Exception{
        Bug bug = new Bug();
        bug.setTitle("New bug");
        bug.setBugId(1L);

        doNothing().when(bugService).deleteBug(bug.getBugId());

        this.mockMvc.perform(delete("/delete-bug?bug_id=" + bug.getBugId().toString())
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
