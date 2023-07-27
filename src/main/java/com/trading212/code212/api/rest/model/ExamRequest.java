package com.trading212.code212.api.rest.model;

import java.time.LocalDateTime;
import java.util.Set;

public record ExamRequest(
        String name,
        LocalDateTime startTime,
        LocalDateTime endTime,
        Set<Long> problems

) {
}
