package com.trading212.code212.core.models;

import com.trading212.code212.repositories.entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<UserEntity, User> {

    @Override
    public User apply(UserEntity userEntity) {
        return new User(
                userEntity.getFirstName(),
                userEntity.getLastName(),
                userEntity.getEmail(),
                userEntity.getRoles());
    }
}

