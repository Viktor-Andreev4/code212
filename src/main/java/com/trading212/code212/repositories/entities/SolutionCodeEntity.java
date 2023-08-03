package com.trading212.code212.repositories.entities;

public record SolutionCodeEntity(
        Long id,
        String codeUrl,
        Long userId,
        int problemId,
        int languageId,
        int statusId
) {
}
