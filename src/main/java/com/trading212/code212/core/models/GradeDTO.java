package com.trading212.code212.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeDTO {
    private Long id;
    private UserDTO user;
    private Integer grade;
    private String report;
    private ProblemDTO problem;

    public GradeDTO(UserDTO user, Integer grade, String report, ProblemDTO problem) {
        this(null, user, grade, report, problem);
    }
}
