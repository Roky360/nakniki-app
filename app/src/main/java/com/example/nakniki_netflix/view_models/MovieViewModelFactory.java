package com.example.nakniki_netflix.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.nakniki_netflix.repositories.MovieRepository;

public class MovieViewModelFactory implements ViewModelProvider.Factory {
    private final MovieRepository repository;

    public MovieViewModelFactory(MovieRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(MovieViewModel.class)) {
            return (T) new MovieViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
