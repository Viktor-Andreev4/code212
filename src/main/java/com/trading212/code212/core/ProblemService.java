package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.ProblemRequest;
import com.trading212.code212.core.models.ProblemDTO;
import com.trading212.code212.repositories.ProblemRepository;
import com.trading212.code212.repositories.entities.ProblemEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;

    public ProblemService(ProblemRepository problemRepository) {
        this.problemRepository = problemRepository;
    }

    public ProblemDTO getProblemById(Long id) {
        return null;
    }

    public List<ProblemDTO> getAllProblems() {
        return problemRepository.getAllProblems()
                .stream()
                .map(Mappers::fromProblemEntity)
                .toList();
    }

    public ProblemDTO createProblem(ProblemRequest request) {

        ProblemEntity problem = problemRepository.createProblem(
                request.title(),
                request.description(),
                request.inputUrl(),
                request.outputUrl()
        );
        return Mappers.fromProblemEntity(problem);
    }
}
