package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.models.*;
import com.trading212.code212.repositories.CodeRepository;
import com.trading212.code212.repositories.LanguageRepository;
import com.trading212.code212.s3.S3Buckets;
import com.trading212.code212.s3.S3Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
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
    private final S3Buckets s3Buckets;
    private final JudgeOApi judgeOApi;
    private String SUBMISSIONS_URL = "https://89f6-149-62-206-206.ngrok-free.app/api/v1/code/submissions/";
    private Map<Long, Set<String>> userSubmissions = new ConcurrentHashMap<>();


    public CodeService(CodeRepository codeRepository, LanguageRepository languageRepository, ProblemService problemService, S3Service s3Service, S3Buckets s3Buckets, JudgeOApi judgeOApi) {
        this.codeRepository = codeRepository;
        this.languageRepository = languageRepository;
        this.problemService = problemService;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.judgeOApi = judgeOApi;

    }

    public SolutionCodeDTO insertSolutionCode() {


        String res = "1";//executeCode(request.code());
        // check the output
        // get the status

        // access the code

        //int languageId = languageRepository.getLanguageByName().id();
//        String codeLink = "vzemi go ot s3";
//        int statusId = 1;
//
//        SolutionCodeEntity solutionCodeEntity = codeRepository.insertSolutionCode(
//                codeLink,
//                request.userId(),
//                request.problemId(),
//                languageId,
//                statusId
//        );
//
//        SolutionCodeDTO solutionCodeDTO = Mappers.fromSolutionCodeEntity(solutionCodeEntity);
//        //TODO REMOVE THIS
//        solutionCodeDTO.setCodeLink(res);
//        return solutionCodeDTO;
        return null;
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

        int testCases = encodedInput.size();
        System.out.println("Encoded input: " + encodedInput);
        int languageId = 52; // 62 - java 52 - c++//languageRepository.getLanguageByName(request.language()).id();

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

        // DEBUG
        submissionRequests.stream().forEach(submissionRequest -> {
            System.out.println(submissionRequest.getCallbackUrl());
        }   );

        try {
            List<TokenResponse> tokenResponses = judgeOApi.executeBatchCode(submissionRequests);
            userSubmissions.put(request.userId(), tokenResponses.stream().map(TokenResponse::getToken).collect(Collectors.toSet()));
            while(userSubmissions.get(request.userId()).size() != 0) {
                Thread.sleep(1000);
                //System.out.println("Waiting for the results...");
            }
            tokenResponses.stream().forEach(tokenResponse -> System.out.println(tokenResponse.getToken()));

            return getBatchCodeResponse(tokenResponses.stream().map(TokenResponse::getToken).collect(Collectors.toList()));

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
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


    public void removeSubmissionFromWaitingList(SubmissionResponse submission, Long userId) {
        if (userSubmissions.containsKey(userId) && userSubmissions.get(userId).contains(submission.getToken())) {
            userSubmissions.get(userId).remove(submission.getToken());
        }
    }
}
