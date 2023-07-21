package com.trading212.code212.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
