package com.trading212.code212.auth;

import com.trading212.code212.core.models.User;
import com.trading212.code212.core.models.UserDTOMapper;
import com.trading212.code212.repositories.entities.UserEntity;
import com.trading212.code212.security.jwt.JWTService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserDTOMapper userDTOMapper;
    private final JWTService jwtService;


    public AuthenticationService(AuthenticationManager authenticationManager, UserDTOMapper userDTOMapper, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userDTOMapper = userDTOMapper;
        this.jwtService = jwtService;
    }


    public AuthenticationResponse login(AuthenticationRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );
        UserEntity principal = (UserEntity) authentication.getPrincipal();
        User user = userDTOMapper.apply(principal);
        String token = jwtService.issueToken(user.getEmail(), user.getEmail());
        return new AuthenticationResponse(token, user);
    }
}
