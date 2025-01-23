package com.example.nakniki_netflix.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.nakniki_netflix.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    LiveData<List<Category>> getAll();

    @Query("SELECT * FROM categories WHERE id = :id")
    LiveData<Category> get(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Category category);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
