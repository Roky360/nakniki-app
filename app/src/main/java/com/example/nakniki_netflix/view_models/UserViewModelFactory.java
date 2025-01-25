package com.example.nakniki_netflix.view_models;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.nakniki_netflix.repositories.UserRepository;

public class UserViewModelFactory implements ViewModelProvider.Factory {
    private final UserRepository repository;

    public UserViewModelFactory(UserRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @SuppressWarnings("unchecked")
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserViewModel.class)) {
            return (T) new UserViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
