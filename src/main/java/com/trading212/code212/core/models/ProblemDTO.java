package com.trading212.code212.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ProblemDTO {
    private Long id;
    private String title;
    private String description;
    private String inputUrl;
    private String outputUrl;

}
