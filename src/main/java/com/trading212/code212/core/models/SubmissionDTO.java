package com.trading212.code212.core.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

// TODO turn into record
@Data
@AllArgsConstructor
public class SubmissionDTO {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionDTO that = (SubmissionDTO) o;
        return languageId == that.languageId && Objects.equals(sourceCode, that.sourceCode) && Objects.equals(stderr, that.stderr) && Objects.equals(status, that.status) && Objects.equals(createdAt, that.createdAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceCode, languageId, stderr, status, createdAt);
    }
}
