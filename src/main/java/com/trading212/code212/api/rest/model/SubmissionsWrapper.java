package com.trading212.code212.api.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class SubmissionsWrapper {
    private List<SubmissionResponse> submissions;
}