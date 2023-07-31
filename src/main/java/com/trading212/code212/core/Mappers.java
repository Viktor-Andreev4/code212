package com.trading212.code212.core;


import com.trading212.code212.core.models.ExamDTO;
import com.trading212.code212.core.models.GradeDTO;
import com.trading212.code212.core.models.ProblemDTO;
import com.trading212.code212.core.models.UserDTO;
import com.trading212.code212.repositories.entities.ExamEntity;
import com.trading212.code212.repositories.entities.GradeEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;
import com.trading212.code212.repositories.entities.UserEntity;

import java.util.stream.Collectors;

class Mappers {

    public static ProblemDTO fromProblemEntity(ProblemEntity problemEntity) {
        return new ProblemDTO(
            problemEntity.id(),
            problemEntity.title(),
            problemEntity.description(),
            problemEntity.inputUrl(),
            problemEntity.outputUrl()
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
//    public static GradeDTO fromGradeEntity(GradeEntity gradeEntity) {
//        return new GradeDTO(
//                gradeEntity.id(),
//                gradeEntity
//        );
//    }

}
