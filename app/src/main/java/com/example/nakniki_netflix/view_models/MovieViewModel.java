package com.example.nakniki_netflix.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.CategoryWithMovies;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.repositories.MovieRepository;

import java.util.List;

public class MovieViewModel extends ViewModel {
    private final MovieRepository repository;

    public MovieViewModel(MovieRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<Movie>> getMovie(String id) {
        return repository.getMovie(id);
    }

    public LiveData<Resource<List<CategoryWithMovies>>> getMoviesByCategories() {
        return repository.getMoviesByCategories();
    }

    public LiveData<Resource<Void>> addWatchedMovie(String movieId) {
        return repository.addWatchedMovie(movieId);
    }

    public LiveData<Resource<List<Movie>>> getRecommendedMovies(String movieId) {
        return repository.getRecommendedMovies(movieId);
    }

    public LiveData<Resource<List<Movie>>> searchMovies(String query) {
        return repository.searchMovies(query);
    }
}
