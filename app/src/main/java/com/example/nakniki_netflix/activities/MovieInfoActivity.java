package com.example.nakniki_netflix.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.repositories.CategoryRepository;
import com.example.nakniki_netflix.view_models.CategoryViewModel;
import com.example.nakniki_netflix.view_models.CategoryViewModelFactory;
import com.example.nakniki_netflix.view_models.ViewModelUtils;
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

        // Navigates to the watch movie activity
        watchNowButton.setOnClickListener(v -> navigateToWatchMovie(movie));
    }

    private void populateMovieDetails(Movie movie) {
        // Load movie thumbnail
        if (movie.getThumbnail() != null && !movie.getThumbnail().isEmpty()) {
            Glide.with(this)
                    .load(getResources().getString(R.string.api_base_url) + movie.getThumbnail().replace("/api/", ""))
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
                CategoryViewModel categoryViewModel = new ViewModelProvider(this, new CategoryViewModelFactory(new CategoryRepository())).get(CategoryViewModel.class);
                LiveData<Resource<Category>> live = categoryViewModel.getCategoryById(category);

                // create the category badge
                CategoryBadge badge = new CategoryBadge(this);
                ViewModelUtils.observeUntil(live, resource -> {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        badge.setCategoryName(resource.getData().getName());

                        // Set margins
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                        );
                        layoutParams.setMargins(8, 0, 8, 0);
                        badge.setLayoutParams(layoutParams);

                        // Add badge to the container
                        categoriesContainer.addView(badge);
                    }
                }, resource -> resource.getStatus() == Resource.Status.SUCCESS || resource.getStatus() == Resource.Status.ERROR);
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

    private void navigateToWatchMovie(Movie movie) {
        Intent intent = new Intent(MovieInfoActivity.this, WatchMovieActivity.class);
        intent.putExtra("movieId", movie.getId());  // Pass the movie ID
        startActivity(intent);
    }
}
