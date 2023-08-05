package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.CodeService;
import com.trading212.code212.core.models.SubmissionDTO;
import com.trading212.code212.core.models.SubmissionRequest;
import com.trading212.code212.core.models.SubmissionResponse;
import com.trading212.code212.core.models.TokenResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
public class CodeController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }


    @PostMapping("/execute")
    public List<SubmissionResponse> getBatchCodeResponse(@RequestBody UserCodeRequest request) {
        return codeService.executeCode(request);
    }

    @PutMapping("/submissions/{userId}")
    public void getBatchCodeResponse(@RequestBody SubmissionResponse submission, @PathVariable Long userId) {
        System.out.println("HIT");
        codeService.removeSubmissionFromWaitingList(submission, userId);
    }



    @GetMapping("/input")
    public List<String> getBatchCodeResponse() {
        // TODO CHANGE
        return codeService.getOutputForProblem(40);
    }

}