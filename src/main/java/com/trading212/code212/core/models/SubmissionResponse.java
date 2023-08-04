package com.trading212.code212.core.models;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SubmissionResponse {

    @SerializedName("submissions")
    private List<SubmissionDTO> submissionDTOs;
}
