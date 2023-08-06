package com.trading212.code212.core.models;

import com.trading212.code212.repositories.entities.LanguageEntity;

public record SolutionCodeDTO(
        long codeId,
        UserDTO user,
        ProblemDTO problemDTO,
        LanguageEntity language,
        StatusDTO status
) {
}
