package com.trading212.code212.repositories.entities;

public record GradeEntity(
        Long id,
        Long userId,
        Integer grade,
        Long problemId
) {
}
