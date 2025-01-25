package com.example.nakniki_netflix.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "categories")
public class Category {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @SerializedName("_id")
    private final String id;
    private String name;
    private Boolean promoted;

    // Constructor
    public Category(@NonNull String id,String name, boolean promoted) {
        this.id = id;
        this.name = name;
        this.promoted = promoted;
    }

    // Getters and Setters
    @NonNull
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isPromoted() {
        return promoted;
    }

    public void setPromoted(Boolean promoted) {
        this.promoted = promoted;
    }

}
