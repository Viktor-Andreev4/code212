package com.trading212.code212.repositories.entities;

public record ProblemEntity(
        Long id,
        String title,
        String description,
        String inputUrl,
        String outputUrl) {
}
