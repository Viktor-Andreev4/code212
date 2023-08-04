package com.trading212.code212.core.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Submission {
    @SerializedName("source_code")
    private String sourceCode;
    @SerializedName("language_id")
    private int languageId;
    @SerializedName("stderr")
    private String stderr;
    @SerializedName("status")
    private StatusDTO status;
    @SerializedName("created_at")
    private String createdAt;
}
