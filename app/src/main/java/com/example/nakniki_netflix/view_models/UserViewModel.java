package com.example.nakniki_netflix.view_models;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nakniki_netflix.entities.User;

public class UserViewModel extends ViewModel {
    private MutableLiveData<User> userData;

    public UserViewModel() {
        userData = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUserData() {
        if (userData == null) {
            userData = new MutableLiveData<>();
        }
        return userData;
    }
}
