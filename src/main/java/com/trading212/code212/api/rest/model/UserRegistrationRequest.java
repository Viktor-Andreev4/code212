package com.trading212.code212.api.rest.model;

public record UserRegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password) {
}
