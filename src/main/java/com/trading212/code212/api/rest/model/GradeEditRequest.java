package com.trading212.code212.api.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GradeEditRequest(@JsonProperty int gradeId, String report) {
}
