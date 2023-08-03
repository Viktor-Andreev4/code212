package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.LanguageEntity;

public interface LanguageRepository {
    LanguageEntity getLanguageByName(String language);
}
