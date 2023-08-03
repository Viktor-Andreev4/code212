package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.AuthenticationRequest;
import com.trading212.code212.api.rest.model.AuthenticationResponse;
import com.trading212.code212.core.models.UserDTO;
import com.trading212.code212.core.models.UserDTOMapper;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.UserEntity;
import com.trading212.code212.security.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;
    private final JWTService jwtService;


    public AuthenticationService(AuthenticationManager authenticationManager, UserRepository userRepository, UserDTOMapper userDTOMapper, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userDTOMapper = userDTOMapper;
        this.jwtService = jwtService;
    }


    public AuthenticationResponse login(AuthenticationRequest request) {
        Optional<UserEntity> userEntity = userRepository
                .selectUserByEmail(request.username());

        if (userEntity.isEmpty()) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userEntity.get().getEmail(),
                        request.password(),
                        userEntity.get().getAuthorities()
                )
        );
        UserEntity principal = (UserEntity) authentication.getPrincipal();
        UserDTO userDTO = userDTOMapper.apply(principal);
        List<String> rolesList = new ArrayList<>(userDTO.roles);
        String token = jwtService.issueToken(userDTO.getEmail(), userEntity.get().getId(), rolesList);
        return new AuthenticationResponse(token, userDTO);
    }
}
