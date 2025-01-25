package com.example.nakniki_netflix.entities;

/**
 * Provided to the API to login.
 */
public class UnregisteredUser {
    private String username;
    private String password;
    public UnregisteredUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
}
