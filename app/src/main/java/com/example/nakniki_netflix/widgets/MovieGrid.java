package com.example.nakniki_netflix.widgets;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.adapters.MovieCardAdapter;
import com.example.nakniki_netflix.entities.Movie;

import java.util.List;

public class MovieGrid extends LinearLayout {
    private RecyclerView moviesRecyclerView;
    private MovieCardAdapter movieAdapter;

    /**
     * Constructor
     * @param context The current activity context.
     * @param attrs   The attributes for the widget.
     */
    public MovieGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.movie_grid, this, true);
        moviesRecyclerView = findViewById(R.id.movies_recycler_view);

        // Set the grid to have 2 columns
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(context, 2));
    }

    /**
     * Input the movie cards.
     * @param movies The movies
     */
    public void setMovies(List<Movie> movies) {
        if (movieAdapter == null) {
            movieAdapter = new MovieCardAdapter((Activity) getContext(), movies);
            moviesRecyclerView.setAdapter(movieAdapter);
        } else {
            movieAdapter.setMovies(movies);
            moviesRecyclerView.getAdapter().notifyDataSetChanged();
        }
    }
}