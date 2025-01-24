package com.example.nakniki_netflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.entities.Movie;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieInfoActivity extends AppCompatActivity {

    private ImageView movieThumbnail;
    private TextView movieTitle;
    private TextView movieCategories;
    private TextView moviePublishedDate;
    private TextView movieCast;
    private TextView movieDescription;
    private Button watchNowButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        // Initialize views
        movieThumbnail = findViewById(R.id.movie_thumbnail);
        movieTitle = findViewById(R.id.movie_title);
        movieCategories = findViewById(R.id.movie_categories);
        moviePublishedDate = findViewById(R.id.movie_published_date);
        movieCast = findViewById(R.id.movie_cast);
        movieDescription = findViewById(R.id.movie_description);
        watchNowButton = findViewById(R.id.watch_now_button);

        // Get the passed Movie object from the Intent
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            populateMovieDetails(movie);
        }

        // TODO - navigate to the watch movie
        // watchNowButton.setOnClickListener(v -> navigateToWatchMovie(movie));
    }

    private void populateMovieDetails(Movie movie) {
        // Load movie thumbnail
        if (movie.getThumbnail() != null && !movie.getThumbnail().isEmpty()) {
            Glide.with(this)
                    .load(movie.getThumbnail())
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .into(movieThumbnail);
        } else {
            movieThumbnail.setImageResource(R.drawable.default_avatar); // Default placeholder
        }

        // Set movie details
        movieTitle.setText(movie.getName() != null ? movie.getName() : "Unknown Title");

        // Safely join categories
        if (movie.getCategories() != null) {
            movieCategories.setText(String.join(", ", movie.getCategories()));
        } else {
            movieCategories.setText("Unknown Category");
        }

        // Safely format the published date
        moviePublishedDate.setText(movie.getPublished() != null ? formatDate(movie.getPublished()) : "Unknown Date");

        // Safely join actors
        if (movie.getActors() != null) {
            movieCast.setText(String.join(", ", movie.getActors()));
        } else {
            movieCast.setText("Unknown Actors");
        }

        // Safely set description
        movieDescription.setText(movie.getDescription() != null ? movie.getDescription() : "No description available.");
    }



    // TODO navigate to the watch movie
//    private void navigateToWatchMovie(Movie movie) {
//        Intent intent = new Intent(this, WatchMovieActivity.class);
//        intent.putExtra("movie_id", movie.getId());
//        startActivity(intent);
//    }

    private String formatDate(Date date) {
        try {
            // Format the Date object into a user-friendly string
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy", Locale.getDefault());
            return outputFormat.format(date);
        } catch (Exception e) {
            return "Unknown Date";
        }
    }

}
