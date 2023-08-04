package com.trading212.code212.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.joda.time.DateTime;

@Data
@AllArgsConstructor
public class SubmissionResponse {
    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("language_id")
    private int languageId;
    @JsonProperty("stderr")
    private String stderr;
    @JsonProperty("status")
    private StatusDTO status;
    @JsonProperty("created_at")
    private String createdAt;
}
