package com.example.nakniki_netflix.view_models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nakniki_netflix.MainActivity;
import com.example.nakniki_netflix.api.CategoryAPI;
import com.example.nakniki_netflix.api.RetrofitClient;
import com.example.nakniki_netflix.db.AppDB;
import com.example.nakniki_netflix.db.CategoryDao;
import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private final CategoryRepository repository;
    private final LiveData<List<Category>> categories;

    public CategoryViewModel(CategoryRepository repository) {
//        repository = new CategoryRepository(
//                AppDB.getInstance(MainActivity.getAppContext()).categoryDao(),
//                RetrofitClient.getInstance().create(CategoryAPI.class)
//        );
        this.repository = repository;
        this.categories = repository.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return categories;
    }

    public LiveData<Category> getCategoryById(String id) {
        return repository.getCategoryById(id);
    }

    public void addCategory(Category category) {
        repository.insertCategory(category);
    }
}
