package com.example.nakniki_netflix.view_models;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.User;
import com.example.nakniki_netflix.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private final UserRepository repository;
    private LiveData<Resource<User>> user; // if null - user is logged out

    public UserViewModel(UserRepository repository) {
        this.repository = repository;
    }

    public LiveData<Resource<User>> getUserData() {
        if (user == null) {
            Log.e("Debug", "User is null");
            user = repository.getUser(repository.userId);
        }

        return user;
    }

    public LiveData<Resource<Void>> register(String username, String password, String email, String profilePic) {
        return repository.createUser(username, password, email, profilePic);
    }

    public LiveData<Resource<Void>> login(String username, String password) {
        return repository.login(username, password);
    }

    public LiveData<Resource<Void>> logout() {
        if (user != null && user.getValue() != null) {
            return repository.logout(user.getValue().getData());
        } else {
            return new MutableLiveData<>(Resource.success(null));
        }
    }
}
