package com.trading212.code212.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Objects;

@Data
@AllArgsConstructor
public class SubmissionDTO {
    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("language_id")
    private int languageId;
    @JsonProperty("stdin")
    private String stdin;
    @JsonProperty("expected_output")
    private String expectedOutput;
    @JsonProperty("stdout")
    private String stdout;
    @JsonProperty("stderr")
    private String stderr;
    @JsonProperty("status")
    private StatusDTO status;
    @JsonProperty("created_at")
    private String createdAt;
    private int index;
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
