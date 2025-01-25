package com.example.nakniki_netflix.entities;

public class SignupForm {

    private String username;
    private String password;
    private String email;
    private String profile_pic;

    public SignupForm(String username, String password, String email, String profile_pic) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.profile_pic = profile_pic;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getProfile_pic() {
        return profile_pic;
    }
}
