package com.example.nakniki_netflix.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;

import com.example.nakniki_netflix.db.converters.DateConverter;
import com.example.nakniki_netflix.db.converters.ListConverter;
import com.example.nakniki_netflix.entities.Movie;

import java.util.List;

@Dao
@TypeConverters({ListConverter.class, DateConverter.class})
public interface MovieDao {
    @Query("SELECT * FROM movies")
    List<Movie> getAll();

    @Query("SELECT * FROM movies WHERE id = :id")
    Movie get(String id);

    @Query("SELECT * FROM movies WHERE categories LIKE '%' || :categoryId || '%'")
    List<Movie> getByCategory(String categoryId);

    @Insert
    void insert(Movie movie);

    @Insert
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);
}
