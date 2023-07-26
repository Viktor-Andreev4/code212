package com.trading212.code212.repositories.entities;

public record SolutionCodeEntity(
        Long id,
        String codeUrl,
        Long userId,
        Long problemId,
        Long languageId,
        Long statusId
) {
}
