package com.example.nakniki_netflix.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.bumptech.glide.Glide;
import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.widgets.CategoryBadge;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieInfoActivity extends AppCompatActivity {

    private ImageView movieThumbnail;
    private TextView movieTitle;
    private TextView moviePublishedDate;
    private TextView movieCast;
    private TextView movieDescription;
    private Button watchNowButton;
    private ImageView exitButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);

        // Initialize views
        movieThumbnail = findViewById(R.id.movie_thumbnail);
        movieTitle = findViewById(R.id.movie_title);
        moviePublishedDate = findViewById(R.id.movie_published_date);
        movieCast = findViewById(R.id.movie_cast);
        movieDescription = findViewById(R.id.movie_description);
        watchNowButton = findViewById(R.id.watch_now_button);
        exitButton = findViewById(R.id.exit_button);

        // Get the passed Movie object from the Intent
        Movie movie = (Movie) getIntent().getSerializableExtra("movie");

        if (movie != null) {
            populateMovieDetails(movie);
        }

        // Set an OnClickListener for the exit button
        exitButton.setOnClickListener(v -> {
            finish(); // Close the current activity and return to the previous one
        });

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

        // Set movie title
        movieTitle.setText(movie.getName() != null ? movie.getName() : "Unknown Title");

        // Clear previous badges
        LinearLayout categoriesContainer = findViewById(R.id.categories_container);
        categoriesContainer.removeAllViews();

        // Add categories dynamically
        if (movie.getCategories() != null) {
            for (String category : movie.getCategories()) {
                // Create a badge
                CategoryBadge badge = new CategoryBadge(this);
                badge.setCategoryName(category);

                // Set margins
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                layoutParams.setMargins(8, 0, 8, 0); // Left, Top, Right, Bottom margins
                badge.setLayoutParams(layoutParams);

                // Add badge to the container
                categoriesContainer.addView(badge);
            }
        } else {
            // Handle missing categories
            CategoryBadge badge = new CategoryBadge(this);
            badge.setCategoryName("Unknown Category");
            categoriesContainer.addView(badge);
        }

        // Format published date
        moviePublishedDate.setText(movie.getPublished() != null ? formatDate(movie.getPublished()) : "Unknown Date");

        // Set cast
        if (movie.getActors() != null) {
            movieCast.setText(String.join(", ", movie.getActors()));
        } else {
            movieCast.setText("Unknown Actors");
        }

        // Set description
        movieDescription.setText(movie.getDescription() != null ? movie.getDescription() : "No description available.");

        // TODO add for you movies
    }

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
