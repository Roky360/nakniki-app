package com.example.nakniki_netflix.entities;

public class LoginResult {
    private final String token;
    private final String error;

    public LoginResult(String token, String error) {
        this.token = token;
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public String getError() {
        return error;
    }
}
