package com.trading212.code212.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SolutionCodeDTO {

    private String codeLink;
    private Long userId;
    private int problemId;
    private int languageId;
    private int statusId;


}
