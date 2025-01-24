package com.example.nakniki_netflix.api;

import com.example.nakniki_netflix.entities.Category;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoryAPI {
    @POST("categories")
    Call<Void> createCategory(@Header("Authorization") String token, @Body Category category);

    @GET("categories")
    Call<List<Category>> getAllCategories(@Header("Authorization") String token);

    @GET("categories/{id}")
    Call<Category> getCategory(@Header("Authorization") String token, @Path("id") String id);

    @PATCH("categories/{id}")
    Call<Void> updateCategory(@Header("Authorization") String token, @Path("id") String id, @Body Category category);

    @DELETE("categories/{id}")
    Call<Void> deleteCategory(@Header("Authorization") String token, @Path("id") String id);
}
