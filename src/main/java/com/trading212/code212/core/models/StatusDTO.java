package com.trading212.code212.core.models;

import com.google.gson.annotations.SerializedName;

public class StatusDTO {
    @SerializedName("id")
    private final int id;
    @SerializedName("description")
    private final String description;

    public StatusDTO(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
