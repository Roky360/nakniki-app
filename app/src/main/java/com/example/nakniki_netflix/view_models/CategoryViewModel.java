package com.example.nakniki_netflix.view_models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.repositories.CategoryRepository;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private final CategoryRepository repository;
    private final LiveData<Resource<List<Category>>> categories;

    public CategoryViewModel(CategoryRepository repository) {
        this.repository = repository;
        this.categories = repository.getAllCategories();
    }

    public LiveData<Resource<List<Category>>> getAllCategories() {
        return categories;
    }

    public LiveData<Resource<Category>> getCategoryById(String id) {
        return repository.getCategoryById(id);
    }

    public void addCategory(Category category) {
        repository.insertCategory(category);
    }
}
