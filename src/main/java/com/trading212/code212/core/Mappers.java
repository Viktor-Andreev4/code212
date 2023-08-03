package com.trading212.code212.core;


import com.trading212.code212.core.models.*;
import com.trading212.code212.repositories.entities.*;

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


    //code
    public static SolutionCodeDTO fromSolutionCodeEntity(SolutionCodeEntity solutionCodeEntity) {
        return new SolutionCodeDTO(
                solutionCodeEntity.codeUrl(),
                solutionCodeEntity.userId(),
                solutionCodeEntity.problemId(),
                solutionCodeEntity.languageId(),
                solutionCodeEntity.statusId()
        );
    }



//    public static GradeDTO fromGradeEntity(GradeEntity gradeEntity) {
//        return new GradeDTO(
//                gradeEntity.id(),
//                gradeEntity
//        );
//    }

}
