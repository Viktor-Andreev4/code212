package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.LanguageEntity;

import java.util.Optional;

public interface LanguageRepository {
    Optional<LanguageEntity> getLanguageByName(String language);

    Optional<LanguageEntity> getLanguageById(Integer id);

}
