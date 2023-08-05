package com.trading212.code212.core.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SubmissionRequest {
    @JsonProperty("language_id")
    private int languageId;
    @JsonProperty("source_code")
    private String sourceCode;
    @JsonProperty("stdin")
    private String stdin;
    @JsonProperty("expected_output")
    private String expectedOutput;
    @JsonProperty("callback_url")
    private String callbackUrl;
//    @JsonProperty("compiler_options")
//    private String compilerOptions;
//    @JsonProperty("expected_output")
//    private String expectedOutput;
//    @JsonProperty("cpu_time_limit")
//    private float cpuTimeLimit;
//    @JsonProperty("cpu_extra_time")
//    private float cpuExtraTime;
//    @JsonProperty("memory_limit")
//    private float memoryLimit;
//    @JsonProperty("stack_limit")
//    private int stackLimit;
//    @JsonProperty("max_file_size")
//    private int maxFileSize;

}
