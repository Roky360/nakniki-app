package com.example.nakniki_netflix.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final String id;

    private String username;
    private String email;
    private String profilePic;
    private String token;

    // constructor
    public User(@NonNull String id, String username, String email, String profilePic, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profilePic = profilePic;
        this.token = token;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
