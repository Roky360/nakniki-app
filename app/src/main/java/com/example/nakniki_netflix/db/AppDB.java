package com.example.nakniki_netflix.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.entities.User;

@Database(entities = {User.class, Movie.class, Category.class}, version = 4)
public abstract class AppDB extends RoomDatabase {
    private static volatile AppDB instance;

    public abstract UserDao userDao();

    public abstract CategoryDao categoryDao();

    public abstract MovieDao movieDao();

    public static AppDB getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDB.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDB.class, "nakniki_db")
                            .fallbackToDestructiveMigration() // Optional
                            .build();
                }
            }
        }
        return instance;
    }
}
