package com.trading212.code212.api.rest;

import com.trading212.code212.api.rest.model.UserCodeRequest;
import com.trading212.code212.core.CodeService;
import com.trading212.code212.core.models.SolutionCodeDTO;
import com.trading212.code212.s3.S3Service;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/code")
public class CodeController {

    private final CodeService codeService;
    private final S3Service s3Service;

    public CodeController(CodeService codeService, S3Service s3Service) {
        this.codeService = codeService;
        this.s3Service = s3Service;
    }

    @GetMapping("/{userId}")
    public byte[] getUserCode(
            @PathVariable("userId") Long userId) {
        return codeService.getUserCode(userId);
    }

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public SolutionCodeDTO uploadUserCode(@RequestBody UserCodeRequest request) {
        return codeService.insertSolutionCode(request);
    }

    @GetMapping
    public String test() {
        byte[] printNumbers = s3Service.getObject("code212-problems", "print numbers/input02.txt");
        return new String(printNumbers);
    }

}