package com.trading212.code212.repositories.entities;

public record GradeEntity(
        Long id,
        UserEntity user,
        Integer grade,
        ExamEntity exam
) {
}
