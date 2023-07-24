package com.trading212.code212.repositories.entities;

public record ProblemEntity(
        Integer id,
        String title,
        String description,
        String inputUrl,
        String outputUrl) {
}
