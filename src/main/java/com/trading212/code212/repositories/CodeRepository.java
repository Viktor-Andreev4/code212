package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.SolutionCodeEntity;

import java.util.Set;

public interface CodeRepository {

    SolutionCodeEntity insertSolutionCode(String codeUrl, long userId, int problemId, int languageId, int statusId);

    Set<SolutionCodeEntity> getSolutionCodeProblemByUserId(long userId, long problemId);


}
