package com.example.nakniki_netflix.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nakniki_netflix.entities.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT * FROM categories")
    List<Category> getAll();

    @Query("SELECT * FROM categories WHERE id = :id")
    List<Category> get(String id);

    @Insert
    void insert(Category category);

    @Insert
    void insertAll(Category... categories);

    @Delete
    void delete(Category category);
}
