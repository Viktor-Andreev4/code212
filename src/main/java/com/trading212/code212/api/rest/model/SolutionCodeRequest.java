package com.trading212.code212.api.rest.model;

public record SolutionCodeRequest(
        long userId,
        int problemId,
        int languageId,
        int statusId
) {

}
