package com.trading212.code212.core;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.trading212.code212.api.rest.model.ProblemRequest;
import com.trading212.code212.core.models.ProblemDTO;

import com.trading212.code212.repositories.ProblemRepository;
import com.trading212.code212.repositories.entities.ProblemEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ProblemService {

    private final ProblemRepository problemRepository;
    private GeneratePresignedUrlRequest generatePresignedUrlRequest;

    @Value("${aws.s3.buckets.problem}")
    private String bucketName;

    private final AmazonS3 s3;

    private Date expiration;

    public ProblemService(ProblemRepository problemRepository, AmazonS3 s3) {
        this.problemRepository = problemRepository;
        this.s3 = s3;
    }

    public ProblemDTO getProblemById(int id) {

        Optional<ProblemEntity> problem = problemRepository.getProblemById(id);
        if (problem.isEmpty()) {
            throw new RuntimeException("Problem not found");
        }
        return Mappers.fromProblemEntity(problem.get());
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

    public String generatePresignedUrl(String objectKey) {
        expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60;
        expiration.setTime(expTimeMillis);

        generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(HttpMethod.PUT)
                        .withExpiration(expiration)
                        .withContentType("text/plain");
        URL url = s3.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }

    public String getProblemNameById(int problemId) {
        Optional<ProblemEntity> problem = problemRepository.getProblemById(problemId);
        if (problem.isEmpty()) {
            throw new RuntimeException("Problem not found");
        }
        return problem.get().title();
    }


}
