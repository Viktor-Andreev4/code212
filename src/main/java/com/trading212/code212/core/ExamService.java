package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.ExamRequest;
import com.trading212.code212.core.models.ExamDTO;
import com.trading212.code212.repositories.ExamProblemRepository;
import com.trading212.code212.repositories.ExamRepository;
import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ExamService {

    private final ExamRepository repository;
    private final ExamProblemRepository examProblemRepository;

    public ExamService(ExamRepository repository, ExamProblemRepository examProblemRepository) {
        this.repository = repository;
        this.examProblemRepository = examProblemRepository;
    }

    public ExamDTO createExam(ExamRequest request) {
        ExamEntity examEntity = repository.createExam(
                request.name(),
                request.startTime(),
                request.endTime()
        );

        for (Long problemId : request.problems()) {
            examProblemRepository.addProblemToExam(
                    examEntity.id(),
                    problemId
            );
        }


        return Mappers.fromExamEntity(examEntity);
    }

    public ExamDTO getUpcomingExam() {
        LocalDateTime now = LocalDateTime.now();
        ExamEntity examEntity = repository.getUpcomingExam(now);
        return Mappers.fromExamEntity(examEntity);
    }
}
