package com.trading212.code212.repositories.entities;

import java.time.LocalDateTime;
import java.util.Set;


public record ExamEntity(
        Long id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate
){

}
