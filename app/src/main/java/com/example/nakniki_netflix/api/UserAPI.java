package com.example.nakniki_netflix.api;

import com.example.nakniki_netflix.entities.LoginResult;
import com.example.nakniki_netflix.entities.UnregisteredUser;
import com.example.nakniki_netflix.entities.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserAPI {
    @POST("tokens")
    Call<LoginResult> login(@Body UnregisteredUser user);

    @GET("users/{id}")
    Call<User> getUser(@Path("id") String id);

    @POST("users")
    Call<Void> createUser(@Body User user);
}
