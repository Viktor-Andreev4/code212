package com.trading212.code212.core.models;

import lombok.Data;

import java.util.Set;

@Data
public class UserDTO {

    public Long id;
    public final String firstName;
    public final String lastName;
    public final String email;
    public Set<String> roles;

    public UserDTO(Long id, String firstName, String lastName, String email, Set<String> roles) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public UserDTO(String firstName, String lastName, String email, Set<String> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}
