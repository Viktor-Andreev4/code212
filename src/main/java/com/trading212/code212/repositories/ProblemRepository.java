package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.ProblemEntity;

import java.util.Optional;

public interface ProblemRepository {

    ProblemEntity createProblem(String title, String description, String inputUrl, String outputUrl);
    Optional<ProblemEntity> getProblemById(Long id);
    void deleteProblemById(Long id);

}
