package com.trading212.code212.repositories;

import java.util.Optional;
import com.trading212.code212.repositories.entities.User;

public interface UserRepository {
    Optional<User> getUserById(Integer id);
    User insertUser(User user);
    boolean existsUserWithEmail(String email);
    boolean existsUserById(Integer userId);
    void deleteUserById(Integer userId);
    Optional<User> selectUserByEmail(String email);
}
