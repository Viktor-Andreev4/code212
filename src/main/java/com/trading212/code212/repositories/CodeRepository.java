package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.SolutionCodeEntity;

import java.util.Optional;
import java.util.Set;

public interface CodeRepository {

    SolutionCodeEntity insertSolutionCode(long userId, int examId, int problemId, int languageId, int statusId);

    Optional<SolutionCodeEntity> getSolutionCodeById(long id);


}
