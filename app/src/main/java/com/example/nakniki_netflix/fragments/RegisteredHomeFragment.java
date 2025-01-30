package com.example.nakniki_netflix.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.VideoView;

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
import com.example.nakniki_netflix.widgets.Alert;
import com.example.nakniki_netflix.widgets.CategoryRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisteredHomeFragment extends Fragment {

    private LinearLayout categoriesRow;
    private Alert alert;
    private MovieViewModel movieViewModel;
    private VideoView videoView;
    private List<Movie> movies = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate fragment layout
        View view = inflater.inflate(R.layout.fragment_registered_home, container, false);

        // Initialize UI elements
        videoView = view.findViewById(R.id.video_player);
        categoriesRow = view.findViewById(R.id.categories_row);
        alert = new Alert(requireActivity());

        // Initialize ViewModel
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository()))
                .get(MovieViewModel.class);

        // Fetch categories and dynamically add them to UI
        fetchCategories();

        return view;
    }

    private void fetchCategories() {
        LiveData<Resource<List<CategoryWithMovies>>> liveData = movieViewModel.getMoviesByCategories();

        liveData.observe(getViewLifecycleOwner(), resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS && resource.getData() != null) {
                movies.clear();
                categoriesRow.removeAllViews();

                for (CategoryWithMovies category : resource.getData()) {
                    movies.addAll(category.getMovies());
                    addCategoryRow(category);
                }
                playRandomMovie();
            } else if (resource.getStatus() == Resource.Status.ERROR) {
                alert.show(resource.getMessage(), "error");
            }
        });
    }

    private void addCategoryRow(CategoryWithMovies categoryWithMovies) {
        CategoryRow categoryRow = new CategoryRow(requireContext(), null);
        categoryRow.setCategory(categoryWithMovies);
        categoriesRow.addView(categoryRow);
    }

    private void playRandomMovie() {
        if (movies.isEmpty()) {
            alert.show("No movies available to play in the autoplayer.", "error");
            return;
        }

        Random random = new Random();
        Movie randomMovie = movies.get(random.nextInt(movies.size()));

        String videoUrl = getResources().getString(R.string.api_movies_base_url) + randomMovie.getId() + ".mp4";
        Log.d("VIDEO_PLAYER", "Playing Video URL: " + videoUrl);

        videoView.setVideoPath(videoUrl);
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(0f, 0f);
            mp.setLooping(true);
            videoView.start();
        });
    }
}
