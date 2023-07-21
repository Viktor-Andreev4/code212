package com.trading212.code212.core.models;

import com.trading212.code212.repositories.entities.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Data
public class User {

    public final String firstName;
    public final String lastName;
    public final String email;
    public Set<Role> roles;

    public User(String firstName, String lastName, String email, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }
}
