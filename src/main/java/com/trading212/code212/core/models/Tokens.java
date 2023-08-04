package com.trading212.code212.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Tokens {
    private List<TokenResponse> tokens;
}
