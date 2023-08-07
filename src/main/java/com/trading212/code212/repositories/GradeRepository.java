package com.trading212.code212.repositories;

import com.trading212.code212.core.models.GradeDTO;
import com.trading212.code212.repositories.entities.GradeEntity;

import java.util.List;
import java.util.Optional;

public interface GradeRepository {

    GradeEntity createGrade(Long userId, Integer grade, String report, int examId, int problemId);
    List<GradeEntity> getStudentGradeForExamProblem(int examId, Long id);

    void editReport(int gradeId, String report);

    GradeEntity getGradeById(int gradeId);
}
