package com.example.nakniki_netflix.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

@Entity(tableName = "movies")
public class Movie {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    private final String id;

    private String name;
    private Date published;
    private List<String> actors;
    private String thumbnail;
    private String description;
    private int length;

    List<String> categories;

    // constructor
    public Movie(@NonNull String id, String name, Date published, List<String> actors, String thumbnail, String description, int length, List<String> categories) {
        this.id = id;
        this.name = name;
        this.published = published;
        this.actors = actors;
        this.thumbnail = thumbnail;
        this.description = description;
        this.length = length;
        this.categories = categories;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
