package com.example.nakniki_netflix.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.VideoView;

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
import com.example.nakniki_netflix.widgets.Alert;
import com.example.nakniki_netflix.widgets.CategoryRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisteredHomeActivity extends AppCompatActivity {

    private LinearLayout categoriesRow;
    private Alert alert;
    private MovieViewModel movieViewModel;
    private VideoView videoView;

    private List<Movie> movies = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_registered);

        // Find where the category rows will be added
        videoView = findViewById(R.id.video_player);
        categoriesRow = findViewById(R.id.categories_row);
        alert = new Alert(this);

        // Initialize ViewModel
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository()))
                .get(MovieViewModel.class);

        // Fetch categories and dynamically add them to UI
        fetchCategories();
    }

    /**
     * Fetch categories from the ViewModel and add them to the screen
     */
    private void fetchCategories() {
        LiveData<Resource<List<CategoryWithMovies>>> liveData = movieViewModel.getMoviesByCategories();

        // Gets all available categories
        liveData.observe(this, resource -> {
            // Clears previous data
            if (resource.getStatus() == Resource.Status.SUCCESS && resource.getData() != null) {
                movies.clear();
                categoriesRow.removeAllViews();
                // Add a category as a new row if there was no error
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

    /**
     * Add a single CategoryRow to the screen
     * @param categoryWithMovies category which contain movies
     */
    private void addCategoryRow(CategoryWithMovies categoryWithMovies) {
        CategoryRow categoryRow = new CategoryRow(this, null);
        categoryRow.setCategory(categoryWithMovies);
        categoriesRow.addView(categoryRow);
    }

    /**
     * Chooses a random movie from the gathered list of movies and plays one
     */
    private void playRandomMovie() {
        if (movies.isEmpty()) {
            alert.show("No movies available to play in the autoplayer.", "error");
            return;
        }
        // Picks a random movie
        Random random = new Random();
        Movie randomMovie = movies.get(random.nextInt(movies.size()));

        // Gets the video url using the movie's id, and pastes it to the video player
        String videoUrl = getResources().getString(R.string.api_movies_base_url) + randomMovie.getId() + ".mp4";
        Log.d("VIDEO_PLAYER", "Playing Video URL: " + videoUrl);
        // Sets the video path to the provided movie
        videoView.setVideoPath(videoUrl);

        //Presets video autoplayer settings
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(0f, 0f); // Mute video
            mp.setLooping(true);  // Loop playback
            videoView.start();    // Autoplay
        });
    }
}


