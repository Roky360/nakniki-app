package com.example.nakniki_netflix.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nakniki_netflix.MainActivity;
import com.example.nakniki_netflix.api.MovieAPI;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.api.RetrofitClient;
import com.example.nakniki_netflix.db.AppDB;
import com.example.nakniki_netflix.db.MovieDao;
import com.example.nakniki_netflix.db.TokenStorage;
import com.example.nakniki_netflix.entities.CategoryWithMovies;
import com.example.nakniki_netflix.entities.Movie;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Response;

public class MovieRepository {
    private final MovieDao movieDao;
    private final MovieAPI movieAPI;
    private final Executor executor;
    private final TokenStorage tokenStorage;

    public MovieRepository() {
        MovieDao movieDao = AppDB.getInstance(MainActivity.getAppContext()).movieDao();
        MovieAPI movieAPI = RetrofitClient.getInstance().create(MovieAPI.class);
        this.movieDao = movieDao;
        this.movieAPI = movieAPI;
        this.executor = Executors.newSingleThreadExecutor();
        this.tokenStorage = TokenStorage.getInstance();
    }

    public LiveData<Resource<List<CategoryWithMovies>>> getMoviesByCategories() {
        MutableLiveData<Resource<List<CategoryWithMovies>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        // get movies by categories from web server
        executor.execute(() -> {
            try {
                Response<List<CategoryWithMovies>> res = movieAPI.getMoviesByCategories(tokenStorage.getToken()).execute();
                List<CategoryWithMovies> categories = res.body();

                if (res.code() == 200 && categories != null) {
                    movieDao.insertAll(
                            categories.stream()
                                    .map(CategoryWithMovies::getMovies)
                                    .flatMap(List::stream).toArray(Movie[]::new)
                    );
                    liveData.postValue(Resource.success(categories));
                } else {
                    liveData.postValue(Resource.error(res.message(), null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<Movie>> getMovie(String movieId) {
        MutableLiveData<Resource<Movie>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                Response<Movie> res = movieAPI.getMovie(tokenStorage.getToken(), movieId).execute();
                Movie movie = res.body();

                if (res.code() == 200 && movie != null) {
                    movieDao.insert(movie);
                    liveData.postValue(Resource.success(movie));
                } else {
                    liveData.postValue(Resource.error(res.message(), null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<Void>> addWatchedMovie(String movieId) {
        MutableLiveData<Resource<Void>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                Response<Void> res = movieAPI.addWatchedMovie(tokenStorage.getToken(), movieId).execute();

                if (res.code() == 204) {
                    liveData.postValue(Resource.success(null));
                } else {
                    liveData.postValue(Resource.error(res.message(), null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<List<Movie>>> getRecommendedMovies(String movieId) {
        MutableLiveData<Resource<List<Movie>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                Response<List<Movie>> res = movieAPI.getRecommendedMovies(tokenStorage.getToken(), movieId).execute();
                List<Movie> movies = res.body();

                if (res.code() == 200 && movies != null) {
                    movieDao.insertAll(movies.toArray(new Movie[0]));
                    liveData.postValue(Resource.success(movies));
                } else {
                    liveData.postValue(Resource.error(res.message(), null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }

    public LiveData<Resource<List<Movie>>> searchMovies(String query) {
        MutableLiveData<Resource<List<Movie>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.loading(null));

        executor.execute(() -> {
            try {
                Response<List<Movie>> res = movieAPI.searchMovies(tokenStorage.getToken(), query).execute();
                List<Movie> movies = res.body();

                if (res.code() == 200 && movies != null) {
                    movieDao.insertAll(movies.toArray(new Movie[0]));
                    liveData.postValue(Resource.success(movies));
                } else {
                    liveData.postValue(Resource.error(res.message(), null));
                }
            } catch (Exception e) {
                liveData.postValue(Resource.error(e.getMessage(), null));
            }
        });

        return liveData;
    }
}
