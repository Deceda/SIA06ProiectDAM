package org.domain.bugfixmanagement.controller.users;

import org.domain.bugfixmanagement.entity.User;
import org.domain.bugfixmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> employees() {
        return ResponseEntity.ok().body(userService.getAllUsers());
    }

    @GetMapping("/get-user")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> getProjectById(@RequestParam(name = "user_id") Long id){
        return ResponseEntity.ok().body(userService.getUserById(id));
    }

    @PostMapping("/add-user")
    public void addUser(@RequestBody UserRequest request) {
        if (request.validateParameters()) {
            userService.addUser(request.getFname(), request.getLname(), request.getEmail(), request.getPassword(), request.getPosition());
        }
    }

    @DeleteMapping("/delete-user")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestParam(name = "user_id") Long id){
        userService.deleteUser(id);
    }

}
