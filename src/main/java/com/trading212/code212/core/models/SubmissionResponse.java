package com.trading212.code212.core.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class SubmissionResponse {

    @JsonProperty("stdin")
    private String stdin;
    @JsonProperty("time")
    private String time;
    @JsonProperty("memory")
    private Integer memory;
    @JsonProperty("token")
    private String token;
    @JsonProperty("stdout")
    private String stdout;
    @JsonProperty("stderr")
    private String stderr;
    @JsonProperty("status")
    private StatusDTO status;
}
