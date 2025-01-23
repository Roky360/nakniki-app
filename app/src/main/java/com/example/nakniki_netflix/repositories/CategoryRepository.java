package com.example.nakniki_netflix.repositories;

import androidx.lifecycle.LiveData;

import com.example.nakniki_netflix.api.CategoryAPI;
import com.example.nakniki_netflix.db.CategoryDao;
import com.example.nakniki_netflix.db.TokenStorage;
import com.example.nakniki_netflix.entities.Category;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final CategoryAPI categoryAPI;
    private final Executor executor;
    private final TokenStorage tokenStorage;

    public CategoryRepository(CategoryDao categoryDao, CategoryAPI categoryAPI) {
        this.categoryDao = categoryDao;
        this.categoryAPI = categoryAPI;
        this.executor = Executors.newSingleThreadExecutor();
        this.tokenStorage = new TokenStorage();
    }

    public LiveData<List<Category>> getAllCategories() {
        refreshCategories();
        return categoryDao.getAll();
    }

    public LiveData<Category> getCategoryById(String id) {
        return categoryDao.get(id);
    }

    public void insertCategory(Category category) {
        executor.execute(() -> {
            categoryDao.insert(category); // Insert into local DB
            categoryAPI.createCategory(tokenStorage.getToken(), category); // Push to remote API
        });
    }

    private void refreshCategories() {
        executor.execute(() -> {
            try {
                List<Category> categories = categoryAPI.getAllCategories(tokenStorage.getToken()).execute().body();
                if (categories != null) {
                    // sync api data to db
                    for (Category category : categories) {
                        categoryDao.insert(category);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace(); // TODO: remove print
            }
        });
    }
}
