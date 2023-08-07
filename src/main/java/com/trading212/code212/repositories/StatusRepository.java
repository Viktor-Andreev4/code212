package com.trading212.code212.repositories;

import com.trading212.code212.repositories.entities.StatusEntity;

import java.util.Optional;

public interface StatusRepository {

    StatusEntity addStatus(int id, String name);
    Optional<StatusEntity> getStatusById(int id);

    Optional<StatusEntity> getStatusByName(String name);
}
