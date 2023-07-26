package com.trading212.code212.api.rest.model;

public record AuthenticationRequest(
        String username,
        String password
) {
}
