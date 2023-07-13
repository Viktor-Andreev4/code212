package com.trading212.code212.core.models;

public class UserDTO {

    public final String firstName;
    public final String lastName;
    public final String email;
    public final Integer problemsSolved;


    public UserDTO(String firstName, String lastName, String email, Integer problemsSolved) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.problemsSolved = problemsSolved;
    }
}
