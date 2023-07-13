package com.trading212.code212.core;

import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }
    public Optional<User> getStudentById(Integer userId){
        return repository.getUserById(userId);
    }
}
