package com.trading212.code212.repositories.entities;

public record GradeEntity(
        Long id,
        Long userId,
        Integer grade,
        String report,
        Integer examId,
        Integer problemId
) {
    public GradeEntity(Long userId, Integer grade, String report, Integer examId,Integer problemId) {
        this(null, userId, grade, report, examId, problemId);
    }
}
