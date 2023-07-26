package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.SolutionCodeEntity;

import java.util.Optional;
import java.util.Set;

public interface SolutionCodeRepository {

    SolutionCodeEntity insertSolutionCode(String codeUrl, long userId, long problemId, long languageId, long statusId);

    Set<SolutionCodeEntity> getSolutionCodeProblemByUserId(long userId, long problemId);


}
