package com.trading212.code212.repositories;

import java.util.List;
import java.util.Optional;
import com.trading212.code212.repositories.entities.UserEntity;

public interface UserRepository {
    Optional<UserEntity> getUserById(Integer id);
    List<UserEntity> getAllUsers();
    UserEntity insertUser(UserEntity user);
    boolean existsUserWithEmail(String email);
    boolean existsUserById(Integer userId);
    void deleteUserById(Integer userId);
    Optional<UserEntity> selectUserByEmail(String email);
}
