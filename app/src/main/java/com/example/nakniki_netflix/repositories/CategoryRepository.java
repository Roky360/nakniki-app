package com.example.nakniki_netflix.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nakniki_netflix.MainActivity;
import com.example.nakniki_netflix.MyApplication;
import com.example.nakniki_netflix.api.CategoryAPI;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.api.RetrofitClient;
import com.example.nakniki_netflix.db.AppDB;
import com.example.nakniki_netflix.db.CategoryDao;
import com.example.nakniki_netflix.db.TokenStorage;
import com.example.nakniki_netflix.entities.Category;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class CategoryRepository {
    private final CategoryDao categoryDao;
    private final CategoryAPI categoryAPI;
    private final Executor executor;
    private final TokenStorage tokenStorage;

    public CategoryRepository() {
        CategoryDao categoryDao = AppDB.getInstance(MyApplication.getAppContext()).categoryDao();
        CategoryAPI categoryAPI = RetrofitClient.getInstance().create(CategoryAPI.class);
        this.categoryDao = categoryDao;
        this.categoryAPI = categoryAPI;
        this.executor = Executors.newSingleThreadExecutor();
        this.tokenStorage = TokenStorage.getInstance();
    }

    public LiveData<Resource<List<Category>>> getAllCategories() {
        MutableLiveData<Resource<List<Category>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));
        refreshCategories(liveData);
        return liveData;
    }

    public LiveData<Resource<Category>> getCategoryById(String id) {
        return new MutableLiveData<>(Resource.success(categoryDao.get(id).getValue()));
    }

    public void insertCategory(Category category) {
        executor.execute(() -> {
            categoryDao.insert(category); // Insert into local DB
//            categoryAPI.createCategory(tokenStorage.getToken(), category); // Push to remote API
        });
    }

    private void refreshCategories(MutableLiveData<Resource<List<Category>>> liveData) {
        executor.execute(() -> {
            try {
                Response<List<Category>> res = categoryAPI.getAllCategories(tokenStorage.getToken()).execute();
                List<Category> categories = res.body();
                if (res.code() == 200 && categories != null) {
                    // sync api data to db
                    categoryDao.insertAll(categories.stream().toArray(Category[]::new));
                    liveData.postValue(Resource.success(categories));
                } else {
                    liveData.postValue(Resource.error(res.message(), null));
                }
            } catch (Exception e) {
                e.printStackTrace(); // TODO: remove print
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });
    }
}
