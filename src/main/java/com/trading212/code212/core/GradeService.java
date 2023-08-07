package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.GradeEditRequest;
import com.trading212.code212.api.rest.model.SubmissionResponse;
import com.trading212.code212.config.OpenAIConfig;
import com.trading212.code212.core.models.GradeDTO;
import com.trading212.code212.core.models.ProblemDTO;
import com.trading212.code212.core.models.UserDTO;
import com.trading212.code212.repositories.GradeRepository;
import com.trading212.code212.repositories.entities.GradeEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;
    private final UserService userService;
    private final ProblemService problemService;
    private final String API_URL = "https://api.openai.com/v1/chat/completions";
    @Value("${openai.api.key}")
    private String openaiApiKey;

    public GradeService(GradeRepository gradeRepository, UserService userService, ProblemService problemService ) {
        this.gradeRepository = gradeRepository;
        this.userService = userService;
        this.problemService = problemService;
    }

    public boolean calculateGrade(List<SubmissionResponse> batchCodeResponse, String code, int examId, int problemId, long userId) {
        int testCases = acceptedTests(batchCodeResponse);
        if (testCases == -1) {
            return false;
        } else if(testCases == 0) {
            testCases = batchCodeResponse.size() / 2;
        }
        String problemDescription = problemService.getProblemById(problemId).getDescription();
        String report = getChatGptResponse(code, problemDescription);
        insertToDatabase(userId, testCases, report, examId, problemId);
        return true;
    }

    private String getChatGptResponse(String code, String problemDescription) {
        RestTemplate restTemplate = new RestTemplate();
        // Build the prompt
        String prompt = "You are a judge of a programming competition. You are given submission and problem description, you need to access the submission by the components. Component number one is code performance with max of 30 points, here you need to assess how good of an submissions big o time complexity and big o space complexity. Component number two is code quality, this is if the user is using good names for the variable and the functions, the max points are 10. ";
        prompt += code + problemDescription;

        return "";
    }

    public int acceptedTests(List<SubmissionResponse> batchCodeResponse) {
        int countStatusAccepted = 0;
        int countStatusWronAsnwers = 0;

        for (SubmissionResponse response : batchCodeResponse) {
            if (response.getStatus() != null) {
                int statusId = response.getStatus().getId();
                if (statusId == 3) {
                    countStatusAccepted++;
                } else if (statusId == 4) {
                    countStatusWronAsnwers++;
                }
                else {
                    return -1;
                }
            }
        }

        if (countStatusAccepted > countStatusWronAsnwers) {
            return countStatusAccepted;
        } else if (countStatusWronAsnwers > countStatusAccepted) {
            return countStatusWronAsnwers;
        } else {
            return 0;
        }
    }

    public GradeDTO insertToDatabase(long userId, int testCases, String report,int examId, int problemId) {
        GradeEntity gradeEntity = gradeRepository.createGrade(userId, testCases, report, examId, problemId);
        UserDTO userDTO = userService.getUserById(userId);
        ProblemDTO problemDTO = problemService.getProblemById(problemId);
        return new GradeDTO(userDTO, testCases, report, problemDTO);
    }

    public List<GradeDTO> getGradeForStudent(int examId, long userId) {
       return  gradeRepository.getStudentGradeForExamProblem(examId, userId)
               .stream()
               .map(gradeEntity -> {
                   UserDTO userDTO = userService.getUserById(userId);
                   ProblemDTO problemDTO = problemService.getProblemById(examId);
                   return new GradeDTO(gradeEntity.id(), userDTO, gradeEntity.grade(), gradeEntity.report(), problemDTO);
               })
               .toList();
    }

    public GradeDTO editReport(GradeEditRequest request) {
        gradeRepository.editReport(request.gradeId(), request.report());
        GradeEntity gradeEntity = gradeRepository.getGradeById(request.gradeId());
        UserDTO userDTO = userService.getUserById(gradeEntity.userId());
        ProblemDTO problemDTO = problemService.getProblemById(gradeEntity.problemId());
        return new GradeDTO(userDTO, gradeEntity.grade(), gradeEntity.report(), problemDTO);
    }
}
