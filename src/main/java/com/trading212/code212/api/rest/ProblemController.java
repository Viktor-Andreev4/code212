package com.trading212.code212.api.rest;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.trading212.code212.api.rest.model.ProblemRequest;
import com.trading212.code212.core.ProblemService;
import com.trading212.code212.core.models.ProblemDTO;
import org.springframework.web.bind.annotation.*;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;

import java.net.URL;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
public class ProblemController {

    private final ProblemService problemService;


    public ProblemController(ProblemService problemService, AmazonS3 s3) {
        this.problemService = problemService;
    }


    @GetMapping
    public List<ProblemDTO> getAllProblems() {
        return problemService.getAllProblems();
    }

    @PostMapping
    public ProblemDTO createProblem(@RequestBody ProblemRequest request) {
        return problemService.createProblem(request);
    }

    @GetMapping("/upload/input")
    public String generateUploadUrlForInput(@RequestParam String problemName, @RequestParam String fileName) {
        String objectKey = problemName + "/input/" + fileName;
        return problemService.generatePresignedUrl(objectKey);
    }

    @GetMapping("/upload/output")
    public String generateUploadUrlForOutput(@RequestParam String problemName, @RequestParam String fileName) {
        String objectKey = problemName + "/output/" + fileName;
        return problemService.generatePresignedUrl(objectKey);
    }



}
