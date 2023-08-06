package com.trading212.code212.core;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.trading212.code212.api.rest.model.*;
import com.trading212.code212.core.models.*;
import com.trading212.code212.repositories.CodeRepository;
import com.trading212.code212.repositories.LanguageRepository;
import com.trading212.code212.repositories.StatusRepository;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.SolutionCodeEntity;
import com.trading212.code212.s3.S3Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final LanguageRepository languageRepository;
    private final ProblemService problemService;
    private final S3Service s3Service;

    //@Value("${aws.s3.buckets.user}")
    private String bucketName = "code212-user-code";
    private final AmazonS3 s3;
    private final JudgeOApi judgeOApi;
    private final String SUBMISSIONS_URL = "https://89f6-149-62-206-206.ngrok-free.app/api/v1/code/submissions/";
    private Map<Long, Set<String>> userSubmissions = new ConcurrentHashMap<>();
    private Date expiration;
    private GeneratePresignedUrlRequest generatePresignedUrlRequest;


    public CodeService(
            CodeRepository codeRepository,
            LanguageRepository languageRepository,
            ProblemService problemService,
            S3Service s3Service,
            AmazonS3 s3,
            JudgeOApi judgeOApi) {

        this.codeRepository = codeRepository;
        this.languageRepository = languageRepository;
        this.problemService = problemService;
        this.s3Service = s3Service;
        this.s3 = s3;
        this.judgeOApi = judgeOApi;

    }

    public SolutionCodeDTO insertSolutionCode(SolutionCodeRequest request) {

        SolutionCodeEntity solutionCodeEntity = codeRepository.insertSolutionCode(
                request.userId(), request.problemId(), request.languageId(),request.statusId()
        );

        return Mappers.fromSolutionCodeEntity(solutionCodeEntity);
    }

    public List<SubmissionResponse> executeCode(UserCodeRequest request) {

        String encodedCode = getEncodedString(request.code());
        List<String> encodedInput = getInputForProblem(request.problemId())
                .stream()
                .map(this::getEncodedString)
                .toList();
        List<String> encodedOutput = getOutputForProblem(request.problemId())
                .stream()
                .map(this::getEncodedString)
                .toList();

        System.out.println("Encoded input: " + encodedInput);
        int languageId = languageRepository.getLanguageByName(request.language()).get().id();

        List<SubmissionRequest> submissionRequests = new ArrayList<>();
        for (int i = 0; i < encodedInput.size(); i++) {
            submissionRequests.add(new SubmissionRequest(
                    languageId,
                    encodedCode,
                    encodedInput.get(i),
                    encodedOutput.get(i),
                    SUBMISSIONS_URL + request.userId()
            ));
        }

        submissionRequests.stream().forEach(submissionRequest -> {
            System.out.println(submissionRequest.getCallbackUrl());
        }   );

        try {
            List<TokenResponse> tokenResponses = judgeOApi.executeBatchCode(submissionRequests);
            userSubmissions.put(request.userId(), tokenResponses.stream().map(TokenResponse::getToken).collect(Collectors.toSet()));
            while(userSubmissions.get(request.userId()).size() != 0) {
                Thread.sleep(1000);
            }
            tokenResponses.stream().forEach(tokenResponse -> System.out.println(tokenResponse.getToken()));

            List<SubmissionResponse> batchCodeResponse = getBatchCodeResponse(tokenResponses.stream().map(TokenResponse::getToken).collect(Collectors.toList()));

            int statusId = giveFinalStatus(batchCodeResponse);
            insertSolutionCode(new SolutionCodeRequest(request.userId(), request.problemId(), languageId, statusId));

            return batchCodeResponse;

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private int giveFinalStatus(List<SubmissionResponse> batchCodeResponse) {
        int status = 3; // Accepted

        for (SubmissionResponse submissionResponse : batchCodeResponse) {
            if (submissionResponse.getStatus().getId() > 4) {
                return submissionResponse.getStatus().getId();
            }
            else if (submissionResponse.getStatus().getId() == 4){
                return submissionResponse.getStatus().getId();
            }
        }

        return status;
    }

    private String getEncodedString(String string) {
        return Base64.getEncoder().encodeToString(string.getBytes());
    }


    public List<SubmissionResponse> getBatchCodeResponse(List<String> tokens) {
        return judgeOApi.getBatchCodeResponse(tokens);
    }

    private List<String> getFilesForProblem(int problemId, String fileType) {
        String problemName = problemService.getProblemNameById(problemId) + "/" + fileType;

        List<byte[]> filesBytes = s3Service.getObjects("code212-problems", problemName);

        List<String> list = filesBytes.stream()
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .collect(Collectors.toList());
        return list;
    }

    public List<String> getOutputForProblem(int problemId) {
        return getFilesForProblem(problemId, "output");
    }

    public List<String> getInputForProblem(int problemId) {
        return getFilesForProblem(problemId, "input");
    }


    public byte[] getUserCode(Long userId) {
        return null;
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


    public void removeSubmissionFromWaitingList(SubmissionResponse submission, Long userId) {
        if (userSubmissions.containsKey(userId) && userSubmissions.get(userId).contains(submission.getToken())) {
            userSubmissions.get(userId).remove(submission.getToken());
        }
    }
}
