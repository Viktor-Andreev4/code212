package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.ExamProblemEntity;

import java.util.Optional;

public interface ExamProblemRepository {

    ExamProblemEntity addProblemToExam(Long examId, Long problemId);
    boolean updateExamProblem(ExamProblemEntity examProblemEntity, Long newProblemId);
    Optional<ExamProblemEntity> getExamProblem(Long examId, Long problemId);
    void deleteExamProblem(Long examId, Long problemId);

}
