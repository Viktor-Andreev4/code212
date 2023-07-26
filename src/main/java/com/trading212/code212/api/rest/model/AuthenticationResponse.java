package com.trading212.code212.api.rest.model;

import com.trading212.code212.core.models.UserDTO;

public record AuthenticationResponse(
        String token,
        UserDTO user
) {
}
