package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.CodeService;
import com.trading212.code212.api.rest.model.SubmissionResponse;
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
        System.out.println("USERCODEREQUEST----------- " + request.examId());
        return codeService.executeCode(request);
    }

    @PutMapping("/submissions/{userId}")
    public void getBatchCodeResponse(@RequestBody SubmissionResponse submission, @PathVariable Long userId) {
        System.out.println("HIT");
        codeService.removeSubmissionFromWaitingList(submission, userId);
    }

    @GetMapping("/upload")
    public String generateUploadUrlForInput(@RequestParam String userId, @RequestParam String problemName, @RequestParam String uuid) {
        String objectKey = userId + "/" + problemName + "/" + uuid;
        return codeService.generatePresignedUrl(objectKey);
    }

}