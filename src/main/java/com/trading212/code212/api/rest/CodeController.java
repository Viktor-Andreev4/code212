package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.CodeService;
import com.trading212.code212.core.models.Submission;
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

    @GetMapping("/{userId}")
    public byte[] getUserCode(
            @PathVariable("userId") Long userId) {
        return codeService.getUserCode(userId);
    }

//    @PostMapping(
//            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
//    )
//    public SolutionCodeDTO uploadUserCode(@RequestBody UserCodeRequest request) {
//        return codeService.insertSolutionCode(request);
//    }

    @PostMapping
    public List<TokenResponse> test(@RequestBody UserCodeRequest request) {
        return codeService.executeCode(request);
    }

    @GetMapping("/batch")
    public List<Submission> getBatchCodeResponse(@RequestParam("token") List<TokenResponse> tokens) {
        return codeService.getBatchCodeResponse(tokens);
    }

    @GetMapping("/input")
    public List<String> getBatchCodeResponse() {
        return codeService.getOutputForProblem(40);
    }


}