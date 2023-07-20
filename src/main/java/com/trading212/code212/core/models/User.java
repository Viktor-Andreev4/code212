package com.trading212.code212.core.models;

public class User {

    public final String firstName;
    public final String lastName;
    public final String email;

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }
}
