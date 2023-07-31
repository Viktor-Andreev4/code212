package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.EnrollUserRequest;
import com.trading212.code212.api.rest.model.ExamRequest;
import com.trading212.code212.core.models.ExamDTO;
import com.trading212.code212.core.models.UserDTO;
import com.trading212.code212.repositories.ExamProblemRepository;
import com.trading212.code212.repositories.ExamRepository;
import com.trading212.code212.repositories.UserExamRepository;
import com.trading212.code212.repositories.entities.ExamEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamProblemRepository examProblemRepository;
    private final UserExamRepository userExamRepository;
    private final LocalDateTime now;

    public ExamService(ExamRepository repository, ExamProblemRepository examProblemRepository, UserExamRepository userExamRepository) {
        this.examRepository = repository;
        this.examProblemRepository = examProblemRepository;
        this.userExamRepository = userExamRepository;
        now = LocalDateTime.now();
    }

    public ExamDTO createExam(ExamRequest request) {
        ExamEntity examEntity = examRepository.createExam(
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
        ExamEntity examEntity = examRepository.getUpcomingOrOngoingExam(now);
        return Mappers.fromExamEntity(examEntity);
    }

    public void enrollUser(EnrollUserRequest request) {
        if(userExamRepository.userIsEnrolled(request.examId(), request.userId())) {
            System.out.println("User is already enrolled");
            return;
        }
        userExamRepository.insertUserExam(request.userId(), request.examId());
    }

    public List<UserDTO> getAllUsersForExamWithId(Long examId) {

        return userExamRepository.getAllUsersForExamWithId(examId)
                .stream()
                .map(Mappers::fromUserEntity)
                .toList();
    }
}
