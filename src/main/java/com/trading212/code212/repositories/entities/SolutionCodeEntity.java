package com.trading212.code212.repositories.entities;

public record SolutionCodeEntity(
        Long id,
        UserEntity user,
        ExamEntity exam,
        ProblemEntity problem,
        LanguageEntity language,
        StatusEntity status
) {
}
