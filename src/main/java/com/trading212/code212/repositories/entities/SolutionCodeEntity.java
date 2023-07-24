package com.trading212.code212.repositories.entities;

public record SolutionCodeEntity(
        Long id,
        String codeUrl,
        UserEntity user,
        ProblemEntity problem,
        String language,
        String status
) {
}
