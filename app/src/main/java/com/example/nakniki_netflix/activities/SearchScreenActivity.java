package com.example.nakniki_netflix.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.repositories.MovieRepository;
import com.example.nakniki_netflix.view_models.MovieViewModel;
import com.example.nakniki_netflix.view_models.MovieViewModelFactory;
import com.example.nakniki_netflix.view_models.ViewModelUtils;
import com.example.nakniki_netflix.widgets.Alert;
import com.example.nakniki_netflix.widgets.MovieGrid;

import java.util.List;

public class SearchScreenActivity extends AppCompatActivity {
    private MovieViewModel movieViewModel;
    private Alert alert;
    private MovieGrid movieGrid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        alert = new Alert(this);

        // initialize grid
        movieGrid = findViewById(R.id.results_layout);

        // setup back button
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // init view model
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository())).get(MovieViewModel.class);


        // listen to "Enter" presses in the search text view
        EditText searchEditText = findViewById(R.id.search_bar);
        Log.d("query", "hi fuck you retard I did not entered");
        searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (    i == android.view.inputmethod.EditorInfo.IME_ACTION_SEARCH ||
                    i == android.view.inputmethod.EditorInfo.IME_ACTION_DONE ||
                    keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                triggerSearch(searchEditText.getText().toString().trim());
                return true;
            }
            return false;
        });
    }

    private void triggerSearch(String query) {
        Log.d("query", "hi fuck you retard I entered");
        if (query.isEmpty()) {
            return;
        }

        ViewModelUtils.observeUntil(movieViewModel.searchMovies(query),
                resource -> {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        List<Movie> movies = resource.getData();
                        Log.d("movies", movies.toString());
                        movieGrid.setMovies(movies);
                        if (movies.isEmpty()) {
                            alert.show(getString(R.string.search_not_found), "info");
                        }
                    } else if (resource.getStatus() == Resource.Status.ERROR) {
                        alert.show(resource.getMessage(), "error");
                    }
                },
                resource -> this.isFinishing() // remove observer when activity is destroyed
        );
    }
}
