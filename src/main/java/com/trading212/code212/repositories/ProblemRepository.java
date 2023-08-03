package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.ProblemEntity;

import java.util.List;
import java.util.Optional;

public interface ProblemRepository {

    ProblemEntity createProblem(String title, String description, String inputUrl, String outputUrl);
    Optional<ProblemEntity> getProblemById(int id);
    void deleteProblemById(Long id);
    List<ProblemEntity> getAllProblems();

}
