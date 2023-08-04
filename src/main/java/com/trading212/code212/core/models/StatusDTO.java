package com.trading212.code212.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StatusDTO {
    @JsonProperty("id")
    private final int id;
    @JsonProperty("description")
    private final String description;

    public StatusDTO(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
