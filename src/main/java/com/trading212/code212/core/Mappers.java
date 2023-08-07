package com.trading212.code212.core;


import com.trading212.code212.core.models.*;
import com.trading212.code212.repositories.ProblemRepository;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.*;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
class Mappers {

    private final UserRepository userRepository;

    private final ProblemRepository problemRepository;

    Mappers(UserRepository userRepository, ProblemRepository problemRepository) {
        this.userRepository = userRepository;
        this.problemRepository = problemRepository;
    }

    public static ProblemDTO fromProblemEntity(ProblemEntity problemEntity) {
        return new ProblemDTO(
            problemEntity.id(),
            problemEntity.title(),
            problemEntity.description()
        );
    }

    public static ExamDTO fromExamEntity(ExamEntity examEntity) {
        return new ExamDTO(
                examEntity.id(),
                examEntity.name(),
                examEntity.startDate(),
                examEntity.endDate(),
                examEntity.problems()
                        .stream()
                        .map(Mappers::fromProblemEntity)
                        .collect(Collectors.toSet())
        );
    }

    public static UserDTO fromUserEntity(UserEntity userEntity) {
        return new UserDTO(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getRoles()
        );
    }


    public static SolutionCodeDTO fromSolutionCodeEntity(SolutionCodeEntity solutionCodeEntity) {
        return new SolutionCodeDTO(
                solutionCodeEntity.id(),
                fromUserEntity(solutionCodeEntity.user()),
                fromProblemEntity(solutionCodeEntity.problem()),
                solutionCodeEntity.language(),
                fromStatusEntity(solutionCodeEntity.status())
        );
    }


    public static StatusDTO fromStatusEntity(StatusEntity statusEntity) {
        return new StatusDTO(
                statusEntity.statusId(),
                statusEntity.name()
        );
    }



}
