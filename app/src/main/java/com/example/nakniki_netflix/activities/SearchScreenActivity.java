package com.example.nakniki_netflix.activities;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.adapters.MovieCardAdapter;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.repositories.MovieRepository;
import com.example.nakniki_netflix.view_models.MovieViewModel;
import com.example.nakniki_netflix.view_models.MovieViewModelFactory;
import com.example.nakniki_netflix.view_models.ViewModelUtils;
import com.example.nakniki_netflix.widgets.Alert;

import java.util.ArrayList;
import java.util.List;

public class SearchScreenActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieCardAdapter movieAdapter;
    private TextView categoryName;
    private MovieViewModel movieViewModel;
    private Alert alert;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        alert = new Alert(this);

        // initialize layout
        LinearLayout searchResultsLayout = findViewById(R.id.results_layout);
        recyclerView = searchResultsLayout.findViewById(R.id.movies_recycler_view);
        categoryName = searchResultsLayout.findViewById(R.id.category_name);

        // setup back button
        ImageView backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(v -> onBackPressed());

        // setup recycler view
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // initialize adapter with an empty list
        movieAdapter = new MovieCardAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(movieAdapter);
        categoryName.setText("");

        // init view model
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository())).get(MovieViewModel.class);


        // listen to "Enter" presses in the search text view
        EditText searchEditText = findViewById(R.id.search_bar);

        searchEditText.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (keyEvent != null && keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                triggerSearch(searchEditText.getText().toString().trim());
                return true;
            }
            return false;
        });
    }

    private void triggerSearch(String query) {
        if (query.isEmpty()) {
            return;
        }

        ViewModelUtils.observeUntil(movieViewModel.searchMovies(query),
                resource -> {
                    if (resource.getStatus() == Resource.Status.SUCCESS) {
                        List<Movie> movies = resource.getData();
                        movieAdapter.setMovies(movies);
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
