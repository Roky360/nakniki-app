package com.example.nakniki_netflix.activities;

import android.os.Bundle;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.api.Resource;
import com.example.nakniki_netflix.entities.CategoryWithMovies;
import com.example.nakniki_netflix.repositories.MovieRepository;
import com.example.nakniki_netflix.view_models.MovieViewModel;
import com.example.nakniki_netflix.view_models.MovieViewModelFactory;
import com.example.nakniki_netflix.widgets.Alert;
import com.example.nakniki_netflix.widgets.CategoryRow;
import java.util.List;

public class RegisteredHomeActivity extends AppCompatActivity {

    private LinearLayout categoriesRow;
    private Alert alert;
    private MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_registered);

        // Find container where category rows will be added
        categoriesRow = findViewById(R.id.categories_row);
        alert = new Alert(this);

        // Initialize ViewModel
        movieViewModel = new ViewModelProvider(this, new MovieViewModelFactory(new MovieRepository()))
                .get(MovieViewModel.class);

        // Fetch categories and dynamically add them to UI
        fetchCategories();
    }

    private void fetchCategories() {
        LiveData<Resource<List<CategoryWithMovies>>> liveData = movieViewModel.getMoviesByCategories();

        liveData.observe(this, resource -> {
            if (resource.getStatus() == Resource.Status.SUCCESS && resource.getData() != null) {
                // Add each category as a new row
                for (CategoryWithMovies category : resource.getData()) {
                    addCategoryRow(category);
                }
            } else if (resource.getStatus() == Resource.Status.ERROR) {
                alert.show(resource.getMessage(), "error");
            }
        });
    }

    private void addCategoryRow(CategoryWithMovies categoryWithMovies) {
        // Create a new CategoryRow instance
        CategoryRow categoryRow = new CategoryRow(this, null);
        categoryRow.setCategory(categoryWithMovies);

        // Add CategoryRow to the LinearLayout container
        categoriesRow.addView(categoryRow);
    }
}
