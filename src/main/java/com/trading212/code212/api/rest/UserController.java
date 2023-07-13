package com.trading212.code212.api.rest;

import com.trading212.code212.core.UserService;
import com.trading212.code212.repositories.entities.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public User getStudent(@PathVariable Integer id){
        return userService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
    }
}
