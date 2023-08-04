package com.trading212.code212.core.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class StatusDTO {
    @SerializedName("id")
    int id;
    @SerializedName("description")
    String description;
}
