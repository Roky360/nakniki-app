package com.example.nakniki_netflix.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.widgets.MovieCard;

import java.util.List;

public class MovieCardAdapter extends RecyclerView.Adapter<MovieCard> {

    private List<Movie> movies;
    private Activity activity;

    public MovieCardAdapter(Activity activity, List<Movie> movies) {
        this.activity = activity;
        this.movies = movies;
    }

    @Override
    public MovieCard onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card, parent, false);
        return new MovieCard(itemView, activity);
    }

    @Override
    public void onBindViewHolder(MovieCard holder, int position) {
        if (movies != null) {
            Movie movie = movies.get(position);
            holder.bind(movie);
        }
    }

    @Override
    public int getItemCount() {
        if (movies != null) {
            return movies.size();
        }
        return 0;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    public List<Movie> getMovies() { return movies; }
}
