package com.trading212.code212.repositories;

import java.util.List;
import java.util.Optional;
import com.trading212.code212.repositories.entities.UserEntity;

public interface UserRepository {
    Optional<UserEntity> getUserById(Long id);
    List<UserEntity> getAllUsers();
    UserEntity insertUser(UserEntity user);
    boolean existsUserWithEmail(String email);
    void insertUserRole(Long userId);
    boolean existsUserById(Long userId);
    void deleteUserById(Long userId);
    Optional<UserEntity> selectUserByEmail(String email);
}
