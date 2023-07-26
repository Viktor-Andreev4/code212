package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.GradeEntity;

import java.util.Optional;

public interface GradeRepository {

    GradeEntity createGrade(Long userId, Integer grade, Long problemId);
    Optional<GradeEntity> getGradeProblem(Long id, Long problemId);

}
