package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.ExamRequest;
import com.trading212.code212.core.ExamService;
import com.trading212.code212.core.models.ExamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/exams")
public class ExamController {

    private final ExamService examService;

    public ExamController(ExamService examService) {
        this.examService = examService;
    }


    @GetMapping
    public ExamDTO getUpcomingExam() {
        return examService.getUpcomingExam();
    }

    @PostMapping
    public ResponseEntity<?> createExam(@RequestBody ExamRequest request) {
        return ResponseEntity.ok(examService.createExam(request));
    }





}
