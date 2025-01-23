package com.example.nakniki_netflix.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nakniki_netflix.MainActivity;
import com.example.nakniki_netflix.db.AppDB;
import com.example.nakniki_netflix.db.CategoryDao;
import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private final CategoryRepository repository;
    private final LiveData<List<Category>> categories;

    public CategoryViewModel() {
        repository = AppDB.getInstance(MainActivity.getAppContext());

    }

}
