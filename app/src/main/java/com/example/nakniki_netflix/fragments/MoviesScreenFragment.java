package com.example.nakniki_netflix.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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

public class MoviesScreenFragment extends Fragment {

    private MovieGrid movieGrid;
    private MovieViewModel movieViewModel;
    private List<Movie> uniqueMovies = new ArrayList<>();
    private static final String TAG = "MOVIES_SCREEN";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movies_screen, container, false);

        // Initialize RecyclerView, setting it to have two movies per row
        movieGrid = view.findViewById(R.id.movies_grid);

        // Initialize ViewModel
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository()))
                .get(MovieViewModel.class);

        // Fetch movies
        fetchMovies();
        return view;
    }

    /**
     * Fetches movies from all categories, removes duplicates, and displays them
     */
    private void fetchMovies() {
        LiveData<Resource<List<CategoryWithMovies>>> liveData = movieViewModel.getMoviesByCategories();
        liveData.observe(getViewLifecycleOwner(), resource -> {
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
            }
        });
    }
}