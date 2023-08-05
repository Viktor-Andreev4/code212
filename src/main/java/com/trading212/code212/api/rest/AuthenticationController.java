package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.AuthenticationRequest;
import com.trading212.code212.api.rest.model.AuthenticationResponse;
import com.trading212.code212.core.AuthenticationService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// TODO MOVE IT TO USER CONTROLLER
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse authenticationResponse = authenticationService.login(request);
        return ResponseEntity.ok()
                .header(HttpHeaders.AUTHORIZATION, authenticationResponse.token())
                .body(authenticationResponse);
    }
}
