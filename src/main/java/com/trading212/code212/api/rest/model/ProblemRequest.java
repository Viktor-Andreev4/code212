package com.trading212.code212.api.rest.model;

public record ProblemRequest(
        String title,
        String description,
        String inputUrl,
        String outputUrl)  {
}
