package com.trading212.code212.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@ToString
public class ExamDTO {
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Set<ProblemDTO> problems;
}
