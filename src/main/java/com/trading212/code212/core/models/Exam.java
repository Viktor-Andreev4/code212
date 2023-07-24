package com.trading212.code212.core.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Exam {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
