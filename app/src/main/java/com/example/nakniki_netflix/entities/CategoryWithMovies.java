package com.example.nakniki_netflix.entities;

import java.util.List;

/**
 * Represents a category with its movies.
 */
public class CategoryWithMovies {
    private String category;
    private List<Movie> movies;

    public CategoryWithMovies(String category, List<Movie> movies) {
        this.category = category;
        this.movies = movies;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
