package com.trading212.code212.auth;

import com.trading212.code212.core.models.User;

public record AuthenticationResponse(
        String token,
        User user
) {
}
