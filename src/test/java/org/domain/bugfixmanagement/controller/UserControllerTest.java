package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.controller.bugs.BugsController;
import org.domain.bugfixmanagement.controller.users.UserController;
import org.domain.bugfixmanagement.entity.Bug;
import org.domain.bugfixmanagement.entity.Project;
import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.service.UserService;
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
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private final UserService userService = Mockito.mock(UserService.class);

    @BeforeEach
    public void setup(){
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setHandlerExceptionResolvers(exceptionResolver())
                .setViewResolvers(viewResolver())
                .build();
    }

    @Test
    public void shouldReturnAllUsers() throws Exception {
        User user = new User(1L,"Ionescu", "Ion", "ion.ionescu@gmail.com", "12345-123", "tester");

        List<User> allUsers = Arrays.asList(user);
        given(userService.getAllUsers()).willReturn(allUsers);
        this.mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].position", is(user.getPosition())));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    public void shouldReturnUserById() throws Exception{
        User user = new User(1L,"Ionescu", "Ion", "ion.ionescu@gmail.com", "12345-123", "tester");
        given(userService.getUserById(any())).willReturn(user);
        this.mockMvc.perform(get(URI.create("/get-user?user_id=1"))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).getUserById(any());
    }

    @Test
    public void shouldDeleteUserById() throws Exception{
        User user = new User();
        user.setId(1L);
        user.setFname("Ionescu");

        doNothing().when(userService).deleteUser(user.getId());

        this.mockMvc.perform(delete("/delete-user?user_id=" + user.getId().toString())
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
