package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.SubmissionRequest;
import com.trading212.code212.api.rest.model.SubmissionResponse;
import com.trading212.code212.api.rest.model.TokenResponse;

import java.io.IOException;
import java.util.List;

public interface JudgeOApi {

    List<TokenResponse> executeBatchCode(List<SubmissionRequest> SubmissionRequest) throws IOException, InterruptedException;
    List<SubmissionResponse> getBatchCodeResponse(List<String> tokens);



}
