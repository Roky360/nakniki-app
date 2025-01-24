package com.example.nakniki_netflix.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.entities.Category;
import com.example.nakniki_netflix.view_models.MovieViewModel;

public class CategoryRow extends LinearLayout {
    // The text above the movie cards, the category name
    private TextView categoryName;
    // The RecyclerView that holds the movie cards
    private RecyclerView moviesRecyclerView;
    private MovieViewModel movieViewModel;

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

    public void setCategory(Category category) {
        categoryName.setText(category.getName());
    }
}