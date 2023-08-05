package com.trading212.code212.core;

import com.trading212.code212.core.models.SubmissionRequest;
import com.trading212.code212.core.models.SubmissionDTO;
import com.trading212.code212.core.models.SubmissionResponse;
import com.trading212.code212.core.models.TokenResponse;

import java.io.IOException;
import java.util.List;

public interface JudgeOApi {

    List<TokenResponse> executeBatchCode(List<SubmissionRequest> SubmissionRequest) throws IOException, InterruptedException;
    List<SubmissionResponse> getBatchCodeResponse(List<String> tokens);

}
