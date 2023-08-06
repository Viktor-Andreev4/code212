package com.trading212.code212.api.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trading212.code212.core.models.StatusDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;


@Data
@AllArgsConstructor
@ToString
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
