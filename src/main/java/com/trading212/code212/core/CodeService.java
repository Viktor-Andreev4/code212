package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.models.SolutionCodeDTO;
import com.trading212.code212.repositories.CodeRepository;
import com.trading212.code212.repositories.LanguageRepository;
import com.trading212.code212.repositories.entities.SolutionCodeEntity;
import com.trading212.code212.s3.S3Buckets;
import com.trading212.code212.s3.S3Service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {

    private final CodeRepository codeRepository;
    private final LanguageRepository languageRepository;
    private final ProblemService problemService;
    private final S3Service s3Service;
    private final S3Buckets s3Buckets;

    public CodeService(CodeRepository codeRepository, LanguageRepository languageRepository, ProblemService problemService, S3Service s3Service, S3Buckets s3Buckets) {
        this.codeRepository = codeRepository;
        this.languageRepository = languageRepository;
        this.problemService = problemService;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
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


    private List<String> getInputForProblem(int problemId) {
        // get the input for the problem

        return null;
    }


    public byte[] getUserCode(Long userId) {
        return null;
    }


}
