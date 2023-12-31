package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.UserRegistrationRequest;
import com.trading212.code212.core.UserService;
import com.trading212.code212.repositories.entities.UserEntity;
import com.trading212.code212.security.jwt.JWTService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JWTService jwtService;

    public UserController(UserService userService, JWTService jwtToken) {
        this.userService = userService;
        this.jwtService = jwtToken;
    }

    @GetMapping
    public List<UserEntity> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<?> registerUser(
            @RequestBody UserRegistrationRequest request){


        Long id = userService.registerUser(request).getId();
        String jwtToken = jwtService.issueToken(request.email(), id, "ROLE_USER");
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, jwtToken)
                .build();
    }

    @GetMapping(value = "/{id}")
    public UserEntity getStudent(@PathVariable Long id){
        return userService.getStudentById(id)
                .orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
    }

    @GetMapping("/email/{email}")
    public Long getUserIdByEmail(@PathVariable String email){
        return userService.getUserIdByEmail(email);
    }





}
