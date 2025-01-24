package com.example.nakniki_netflix.widgets;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nakniki_netflix.R;
import com.example.nakniki_netflix.entities.Movie;
import com.example.nakniki_netflix.activities.MovieInfoActivity;

public class MovieCard extends RecyclerView.ViewHolder {

    // Movie title TextView
    private final TextView movieName;

    // Movie thumbnail ImageView
    private final ImageView thumbnail;

    // Activity reference
    private final Activity activity;

    /**
     * Constructor initializes the views from the layout.
     * @param itemView The root view for this card.
     * @param activity The Activity context for showing alerts.
     */
    public MovieCard(View itemView, Activity activity) {
        super(itemView);
        this.activity = activity; // Assign the Activity reference
        movieName = itemView.findViewById(R.id.movie_name);
        thumbnail = itemView.findViewById(R.id.movie_thumbnail);
    }

    /**
     * Binds a Movie object to the card, populating the data into the views.
     * @param movie The Movie object containing data to display.
     */
    public void bind(@NonNull Movie movie) {
        // Set the movie name
        movieName.setText(movie.getName());

        // Load the movie thumbnail using Glide
        if (movie.getThumbnail() != null && !movie.getThumbnail().isEmpty()) {
            Glide.with(thumbnail.getContext())
                    .load(movie.getThumbnail())
                    .placeholder(R.drawable.default_avatar)
                    .error(R.drawable.default_avatar)
                    .into(thumbnail);
        } else {
            thumbnail.setImageResource(R.drawable.default_avatar); // Default placeholder
        }

        // when the user press the movie card
        itemView.setOnClickListener(v -> {
            // TODO redirect to the movie details page
            Alert alert = new Alert(activity);
            alert.show("You clicked on " + this.movieName.getText(), "success");

            Intent intent = new Intent(activity, MovieInfoActivity.class);
            intent.putExtra("movie", movie); // Pass the movie object
            activity.startActivity(intent);
        });
    }
}
