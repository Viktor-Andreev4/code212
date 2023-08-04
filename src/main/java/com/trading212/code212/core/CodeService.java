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
import java.util.stream.Collectors;

@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final LanguageRepository languageRepository;
    private final ProblemService problemService;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;
    private final JudgeOApi judgeOApi;

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

    public List<SubmissionDTO> executeCode(UserCodeRequest request) {
        String encodedString = Base64.getEncoder().encodeToString(request.code().getBytes());
        List<String> encodedInput = getInputForProblem(request.problemId());

        int languageId = 52; // 62 - java 52 - c++//languageRepository.getLanguageByName(request.language()).id();

        List<SubmissionRequest> submissionRequests = new ArrayList<>();
        for (int i = 0; i < encodedInput.size(); i++) {
            submissionRequests.add(new SubmissionRequest(
                    languageId,
                    encodedString,
                    encodedInput.get(i)
            ));
        }
        try {
            List<TokenResponse> tokenResponses = judgeOApi.executeBatchCode(submissionRequests);
            try {
                Thread.sleep(4000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            return getBatchCodeResponse(tokenResponses);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // TODO REMOVE
    private List<SubmissionDTO> validateSubmission(List<SubmissionDTO> checkedSubmissions) {
        List<SubmissionDTO> submissionDTOS = new ArrayList<>();
        int statusId;
        for(SubmissionDTO submissionDTO : checkedSubmissions) {
            statusId = submissionDTO.getStatus().getId();
            if(statusId == 3) {
                submissionDTOS.add(submissionDTO);
            }
            else if(statusId > 4){
                submissionDTOS.clear();
                submissionDTOS.add(submissionDTO);
                return submissionDTOS;
            }
        }
        return submissionDTOS;
    }

    public List<SubmissionDTO> getBatchCodeResponse(List<TokenResponse> tokens) {
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



}
