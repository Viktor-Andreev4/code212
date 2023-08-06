package com.trading212.code212.api.rest.model;


public record UserCodeRequest(String code, Long userId, int examId, int problemId, String language) {
}
