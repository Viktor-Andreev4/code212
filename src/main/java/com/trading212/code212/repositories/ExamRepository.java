package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

public interface ExamRepository {

    ExamEntity createExam(String title, LocalDateTime startDate, LocalDateTime endDate);
    Optional<ExamEntity> getExamById(int id);
    void deleteExamById(int id);
    boolean addProblemToExam(int examID, int problemID);
}
