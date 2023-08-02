package com.trading212.code212.api.rest;

import com.trading212.code212.core.CodeService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/code")
public class CodeController {

    private final CodeService codeService;

    public CodeController(CodeService codeService) {
        this.codeService = codeService;
    }

    @GetMapping("/{userIdd}")
    public byte[] getUserCode(
            @PathVariable("userId") Long userId) {
        return codeService.getUserCode(userId);
    }

    @PostMapping(
            value = "{userId}/code",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public void uploadUserCode(
            @PathVariable("userId") Long userId,
            @RequestParam("problem") Long problemId,
            @RequestParam("file") MultipartFile file) {
        codeService.insertSolutionCode(userId, problemId, file);
    }

}