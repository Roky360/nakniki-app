package com.example.nakniki_netflix.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.CategoryWithMovies;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.repositories.MovieRepository;
import com.example.nakniki_netflix.view_models.MovieViewModel;
import com.example.nakniki_netflix.view_models.MovieViewModelFactory;
import com.example.nakniki_netflix.widgets.MovieGrid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoviesScreenActivity extends AppCompatActivity {

    private MovieGrid movieGrid;
    private MovieViewModel movieViewModel;
    private List<Movie> uniqueMovies = new ArrayList<>();
    private static final String TAG = "MOVIES_SCREEN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_screen);

        // Initialize RecyclerView, setting it to have two movies per row
        movieGrid = findViewById(R.id.movies_grid);

        // Initialize ViewModel
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository()))
                .get(MovieViewModel.class);

        // Fetch movies
        fetchMovies();
    }

    /**
     * Fetches movies from all categories, removes duplicates, and displays them
     */
    private void fetchMovies() {
        LiveData<Resource<List<CategoryWithMovies>>> liveData = movieViewModel.getMoviesByCategories();

        liveData.observe(this, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS && resource.getData() != null) {
                uniqueMovies.clear();
                Set<String> movieIds = new HashSet<>();

                for (CategoryWithMovies category : resource.getData()) {
                    for (Movie movie : category.getMovies()) {
                        if (!movieIds.contains(movie.getId())) {
                            movieIds.add(movie.getId());
                            uniqueMovies.add(movie);
                        }
                    }
                }
                // Set adapter for unique movies
                movieGrid.setMovies(uniqueMovies);

            } else if (resource.getStatus() == Resource.Status.ERROR) {
                Log.e(TAG, "Error fetching movies: " + resource.getMessage());
                Toast.makeText(this, "Failed to load movies.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}