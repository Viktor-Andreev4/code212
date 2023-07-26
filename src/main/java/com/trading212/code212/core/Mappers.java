package com.trading212.code212.core;


import com.trading212.code212.core.models.GradeDTO;
import com.trading212.code212.core.models.ProblemDTO;
import com.trading212.code212.repositories.entities.GradeEntity;
import com.trading212.code212.repositories.entities.ProblemEntity;

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


//    public static GradeDTO fromGradeEntity(GradeEntity gradeEntity) {
//        return new GradeDTO(
//                gradeEntity.id(),
//                gradeEntity
//        );
//    }

}
