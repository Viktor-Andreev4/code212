package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.EnrollUserRequest;
import com.trading212.code212.api.rest.model.ExamRequest;
import com.trading212.code212.core.ExamService;
import com.trading212.code212.core.models.ExamDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollUser(@RequestBody EnrollUserRequest request) {
        examService.enrollUser(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{examId}/participants")
    public ResponseEntity<?> getAllUsersForExamWithId(@PathVariable Long examId) {
        return ResponseEntity.ok(examService.getAllUsersForExamWithId(examId));
    }

    @GetMapping("/all")
    public List<ExamDTO> getAllExams() {
        return examService.getAllExams();
    }
}
