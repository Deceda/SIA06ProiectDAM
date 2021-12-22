package org.domain.bugfixmanagement.controller;

import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EmployeeController {

    private final UserService userService;

    public EmployeeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/employees")
    public List<User> employees() {
        return userService.getAllUsers();
    }

}
