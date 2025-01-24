package com.example.nakniki_netflix.api;

import com.example.nakniki_netflix.entities.CategoryWithMovies;
import com.example.nakniki_netflix.entities.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface MovieAPI {
    @POST("movies")
    Call<Void> createMovie(@Header("Authorization") String token, @Body Movie movie);

    // promoted categories only + watched category
    @GET("movies")
    Call<List<CategoryWithMovies>> getMoviesByCategories(@Header("Authorization") String token);

    @GET("movies/{id}")
    Call<Movie> getMovie(@Header("Authorization") String token, @Path("id") String id);

    /* recommend */
    @POST("movies/{id}/recommend")
    Call<Void> addWatchedMovie(@Header("Authorization") String token, @Path("id") String id);

    @GET("movies/{id}/recommend")
    Call<List<Movie>> getRecommendedMovies(@Header("Authorization") String token, @Path("id") String id);

    /* search */
    @GET("movies/search/{query}")
    Call<List<Movie>> searchMovies(@Header("Authorization") String token, @Path("query") String query);
}
