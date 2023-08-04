package com.trading212.code212.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.trading212.code212.core.models.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

@Service
public class JudgeOImpl implements JudgeOApi {

    private final ObjectMapper mapper;
    private final String tokenSeparator;
    private final Gson gson;

    public JudgeOImpl(Gson gson) {
        this.gson = gson;
        this.mapper = new ObjectMapper();
        tokenSeparator = ",";
    }


    @Override
    public List<TokenResponse> executeBatchCode(List<SubmissionRequest> submissions) {
        String requestBody = generateSubmissionJson(submissions);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/batch?base64_encoded=true"))
                .header("content-type", "application/json")
                .header("Content-Type", "application/json")
                .header("X-RapidAPI-Key", "bdeda95a1dmsh52a5dcfa61ac38cp1095fdjsn5d333499eee4")
                .header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
                .method("POST", HttpRequest
                        .BodyPublishers
                        .ofString(requestBody))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return parseJsonToTokensResponse(response.body());
    }

    private String generateSubmissionJson(List<SubmissionRequest> submissions) {
        Map<String, Object> body = new HashMap<>();
        body.put("submissions", submissions);
        String requestBody = null;
        try {
            requestBody = mapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return requestBody;
    }


    private List<TokenResponse> parseJsonToTokensResponse(String json) {
        Type listType = new TypeToken<List<TokenResponse>>(){}.getType();
        return gson.fromJson(json, listType);
    }

    @Override
    public List<SubmissionDTO> getBatchCodeResponse(List<TokenResponse> tokens) {
        String separatedTokens = concatenateTokenValues(tokens, tokenSeparator);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://judge0-ce.p.rapidapi.com/submissions/batch?tokens=" + separatedTokens + "&fields=*"))
                .header("X-RapidAPI-Key", "bdeda95a1dmsh52a5dcfa61ac38cp1095fdjsn5d333499eee4")
                .header("X-RapidAPI-Host", "judge0-ce.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }


        return parseJsonToSubmissionResponse(response.body()).getSubmissionDTOs();
    }

    private SubmissionResponse parseJsonToSubmissionResponse(String json) {
        return gson.fromJson(json, SubmissionResponse.class);
    }
    private String concatenateTokenValues(List<TokenResponse> tokens, String separator) {
        return String.join(separator, tokens.stream()
                .map(TokenResponse::getToken)
                .toArray(String[]::new));
    }



}
