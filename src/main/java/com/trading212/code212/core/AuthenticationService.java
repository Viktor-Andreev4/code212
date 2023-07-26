package com.trading212.code212.core;

import com.trading212.code212.api.rest.model.AuthenticationRequest;
import com.trading212.code212.api.rest.model.AuthenticationResponse;
import com.trading212.code212.core.models.UserDTO;
import com.trading212.code212.core.models.UserDTOMapper;
import com.trading212.code212.repositories.UserRepository;
import com.trading212.code212.repositories.entities.Role;
import com.trading212.code212.repositories.entities.UserEntity;
import com.trading212.code212.security.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
//        boolean existsUserWithEmail = userRepository
//                .existsUserWithEmail(request.username());
//        if (!existsUserWithEmail) {
//            throw new IllegalArgumentException("Invalid username or password");
//        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        UserEntity principal = (UserEntity) authentication.getPrincipal();
        UserDTO user = userDTOMapper.apply(principal);
        List<String> rolesList = new ArrayList<>();
        for (Role role : user.getRoles()) {
            rolesList.add(role.name());
        }
        String token = jwtService.issueToken(user.getEmail(), rolesList);
        return new AuthenticationResponse(token, user);
    }
}
