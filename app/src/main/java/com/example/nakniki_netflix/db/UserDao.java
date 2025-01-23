package com.example.nakniki_netflix.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nakniki_netflix.entities.User;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM users WHERE id = :id")
    User get(String id);

    @Delete
    void delete(User id);
}
