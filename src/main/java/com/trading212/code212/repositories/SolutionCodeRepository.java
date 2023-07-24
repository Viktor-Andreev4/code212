package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.SolutionCodeEntity;

public interface SolutionCodeRepository {

    SolutionCodeEntity insertSolutionCode(int studentID, int courseID, String code);
    
}
