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
import com.example.nakniki_netflix.adapters.MovieAdapter;

public class CategoryRow extends LinearLayout {
    private TextView categoryTitle;
    private RecyclerView moviesRecyclerView;
    private MovieAdapter movieAdapter;

    public CategoryRow(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.item_category_row, this, true);

        categoryTitle = findViewById(R.id.category_title);
        moviesRecyclerView = findViewById(R.id.movies_recycler_view);

        moviesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
    }

    public void setCategory(Category category) {
        categoryTitle.setText(category.getCategoryName());
        movieAdapter = new MovieAdapter(getContext(), category.getMovies());
        moviesRecyclerView.setAdapter(movieAdapter);
    }
}