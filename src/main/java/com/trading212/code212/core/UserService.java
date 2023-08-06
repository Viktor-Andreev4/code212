package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.UserRegistrationRequest;
import com.trading212.code212.core.models.UserDTO;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.UserEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserEntity registerUser(UserRegistrationRequest request){
        String email = request.email();
        if (existsUserWithEmail(email)){
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }
        UserEntity user = new UserEntity(
                request.firstName(),
                request.lastName(),
                request.email(),
                passwordEncoder.encode(request.password())
        );
        UserEntity userEntity = repository.insertUser(user);
        repository.insertUserRole(userEntity.getId());
        return userEntity;
    }

    private boolean existsUserWithEmail(String email){
        return repository.existsUserWithEmail(email);
    }

    public boolean existsUserWithId(Long userId){
        return repository.existsUserWithId(userId);
    }

    public Optional<UserEntity> getStudentById(Long userId){
        return repository.getUserById(userId);
    }
    public List<UserEntity> getAllUsers(){
        return repository.getAllUsers();
    }

    public Long getUserIdByEmail(String email){
        return repository.getUserIdByEmail(email);
    }

    public UserDTO getUserById(long id) {
        UserEntity userEntity = repository.getUserById(id).orElseThrow(() -> new IllegalArgumentException("User with id " + id + " does not exist"));
        return Mappers.fromUserEntity(userEntity);
    }
}
