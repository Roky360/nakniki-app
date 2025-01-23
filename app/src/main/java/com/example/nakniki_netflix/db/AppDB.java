package com.example.nakniki_netflix.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.entities.User;

@Database(entities = {User.class, Movie.class, Category.class}, version = 1)
public abstract class AppDB extends RoomDatabase {
    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract MovieDao movieDao();
}
