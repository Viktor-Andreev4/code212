package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.ProblemRequest;
import com.trading212.code212.core.ProblemService;
import com.trading212.code212.core.models.ProblemDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/problems")
public class ProblemController {

    private final ProblemService problemService;


    public ProblemController(ProblemService problemService) {
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
}
