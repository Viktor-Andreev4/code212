package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.models.SolutionCodeDTO;
import com.trading212.code212.core.models.SubmissionRequest;
import com.trading212.code212.core.models.Submission;
import com.trading212.code212.core.models.TokenResponse;
import com.trading212.code212.repositories.CodeRepository;
import com.trading212.code212.repositories.LanguageRepository;
import com.trading212.code212.repositories.entities.SolutionCodeEntity;
import com.trading212.code212.s3.S3Buckets;
import com.trading212.code212.s3.S3Service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
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

    public SolutionCodeDTO insertSolutionCode(UserCodeRequest request) {

        // get input for the problem
        // get count for the input files
        // read the files and store them in a list
        // do the same for the output files



        // execute the code
        String res = "1";//executeCode(request.code());
        // check the output
        // get the status

        // access the code

        int languageId = languageRepository.getLanguageByName(request.language()).id();
        String codeLink = "vzemi go ot s3";
        int statusId = 1;

        SolutionCodeEntity solutionCodeEntity = codeRepository.insertSolutionCode(
                codeLink,
                request.userId(),
                request.problemId(),
                languageId,
                statusId
        );

        SolutionCodeDTO solutionCodeDTO = Mappers.fromSolutionCodeEntity(solutionCodeEntity);
        //TODO REMOVE THIS
        solutionCodeDTO.setCodeLink(res);
        return solutionCodeDTO;
    }

    public List<TokenResponse> executeCode(UserCodeRequest request) {
        String encodedString = Base64.getEncoder().encodeToString(request.code().getBytes());
        List<SubmissionRequest> submissionRequests = new ArrayList<>();
            submissionRequests.add(new SubmissionRequest(
                    62,
                    encodedString,
                    "MQ==" // 1
            ));
        submissionRequests.add(new SubmissionRequest(
                62,
                encodedString,
                "Mg==" // 2
        ));
        submissionRequests.add(new SubmissionRequest(
                62,
                encodedString,
                "Mw==" //3
        ));

        try {
            return judgeOApi.executeBatchCode(submissionRequests);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Submission> getBatchCodeResponse(List<TokenResponse> tokens) {
        return judgeOApi.getBatchCodeResponse(tokens);
    }
    public List<String> getFilesForProblem(int problemId, String fileType) {
        String problemName = problemService.getProblemNameById(problemId) + "/" + fileType;

        List<byte[]> filesBytes = s3Service.getObjects("code212-problems", problemName);

        List<String> list = filesBytes.stream()
                .map(bytes -> new String(bytes, StandardCharsets.UTF_8))
                .collect(Collectors.toList());
        System.out.println(list);
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
