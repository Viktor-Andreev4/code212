package com.trading212.code212.repositories.entities;

import java.time.LocalDateTime;


public record ExamEntity(
        Long id,
        String name,
        LocalDateTime startDate,
        LocalDateTime endDate
){

}
