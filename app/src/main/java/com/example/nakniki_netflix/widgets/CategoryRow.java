package com.example.nakniki_netflix.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.adapters.MovieCardAdapter;
import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.entities.CategoryWithMovies;
import com.example.nakniki_netflix.view_models.MovieViewModel;

public class CategoryRow extends LinearLayout {
    // The text above the movie cards, the category name
    private TextView categoryName;
    // The RecyclerView that holds the movie cards
    private RecyclerView moviesRecyclerView;
    private MovieViewModel movieViewModel;

    /**
     * Constructor, a category which contains movies which belong to it
     * @param context The context of the activity
     * @param attrs The attributes of the view
     */
    public CategoryRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initiates the Category row
     * @param context
     */
    private void init(Context context) {
        // Accessing the category_row XML, Creates a new ID
        categoryName = findViewById(R.id.category_name);
        moviesRecyclerView = findViewById(R.id.movies_recycler_view);
        // Sets te RecyclerView to be horizontal
        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    /**
     * Sets the category name and the movies in the category
     * @param categoryWithMovies A category... with movies!!
     */
    public void setCategory(CategoryWithMovies categoryWithMovies) {
        categoryName.setText(categoryWithMovies.getCategory());
        MovieCardAdapter movieAdapter = new MovieCardAdapter((Activity) getContext(), categoryWithMovies.getMovies());
        moviesRecyclerView.setAdapter(movieAdapter);
    }
}