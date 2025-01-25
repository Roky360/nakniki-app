package com.example.nakniki_netflix.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nakniki_netflix.MainActivity;
import com.example.nakniki_netflix.MyApplication;
import com.example.nakniki_netflix.api.CategoryAPI;
import com.example.nakniki_netflix.api.ErrorResponse;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.api.RetrofitClient;
import com.example.nakniki_netflix.api.UserAPI;
import com.example.nakniki_netflix.db.AppDB;
import com.example.nakniki_netflix.db.CategoryDao;
import com.example.nakniki_netflix.db.TokenStorage;
import com.example.nakniki_netflix.db.UserDao;
import com.example.nakniki_netflix.entities.LoginResult;
import com.example.nakniki_netflix.entities.SignupForm;
import com.example.nakniki_netflix.entities.UnregisteredUser;
import com.example.nakniki_netflix.entities.User;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

import retrofit2.Response;

public class UserRepository {
    private final UserDao userDao;
    private final UserAPI userAPI;
    private final Executor executor;
    private final TokenStorage tokenStorage;
    public String userId = null; // represents the user id if registered (null if not)

    public UserRepository() {
        UserDao categoryDao = AppDB.getInstance(MyApplication.getAppContext()).userDao();
        UserAPI categoryAPI = RetrofitClient.getInstance().create(UserAPI.class);
        this.userDao = categoryDao;
        this.userAPI = categoryAPI;
        this.executor = Executors.newSingleThreadExecutor();
        this.tokenStorage = TokenStorage.getInstance();
    }

    public LiveData<Resource<Void>> login(String username, String password) {
        MutableLiveData<Resource<Void>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                UnregisteredUser loginForm = new UnregisteredUser(username, password);
                Response<LoginResult> res = userAPI.login(loginForm).execute();
                LoginResult loginResult = res.body();

                if (res.code() == 200 && loginResult != null) {
                    tokenStorage.saveToken(loginResult.getToken());
                    liveData.postValue(Resource.success(null));

                    DecodedJWT jwt = JWT.decode(loginResult.getToken());
                    String userId = jwt.getClaim("user_id").asString();
                    getUser(userId);
                } else {
                    if (loginResult != null) {
                        liveData.postValue(Resource.error(loginResult.getError(), null));
                    } else {
                        String errorMessage = ErrorResponse.getErrorMessage(res);
                        liveData.postValue(Resource.error(errorMessage, null));
                    }
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<User>> getUser(String userId) {
        MutableLiveData<Resource<User>> liveData = new MutableLiveData<>();
        // if there is no token then the user is logged out
        if (tokenStorage.getTokenRaw() == null) {
            liveData.setValue(Resource.error("User not logged in.", null));
            return liveData;
        }
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                Response<User> res = userAPI.getUser(userId).execute();
                User user = res.body();

                if (res.code() == 200 && user != null) {
                    userDao.insert(user);
                    liveData.postValue(Resource.success(user));
                } else {
                    String errorMessage = ErrorResponse.getErrorMessage(res);
                    liveData.postValue(Resource.error(errorMessage, null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<Void>> createUser(String username, String password, String email, String profilePic) {
        MutableLiveData<Resource<Void>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                SignupForm signupForm = new SignupForm(username, password, email, profilePic);
                Response<Void> res = userAPI.createUser(signupForm).execute();

                if (res.code() == 201) {
                    liveData.postValue(Resource.success(null));
                } else {
                    String errorMessage = ErrorResponse.getErrorMessage(res);
                    liveData.postValue(Resource.error(errorMessage, null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<Void>> logout(User user) {
        MutableLiveData<Resource<Void>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            userDao.delete(user);
            tokenStorage.clearToken();
            this.userId = null;
            liveData.postValue(Resource.success(null));
        });

        return liveData;
    }
}
