package com.example.nakniki_netflix.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = false)
    @SerializedName("_id")
    @NonNull
    private final String id;

    private String username;
    private String email;
    private String profile_pic;

    // constructor
    public User(@NonNull String id, String username, String email, String profile_pic) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.profile_pic = profile_pic;
    }

    // Getters and Setters
    @NonNull
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

    public String getProfile_pic() {
        return profile_pic;
    }
}
