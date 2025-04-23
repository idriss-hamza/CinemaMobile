package com.example.cinemaapp.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.models.Movie;
import java.util.List;
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private List<Movie> movies;
    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }
    @Override
    public MovieViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }
    @Override
    public void onBindViewHolder( MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.titleTextView.setText(movie.getTitle());
        holder.genreTextView.setText(movie.getGenre());
        holder.descriptionTextView.setText(movie.getDescription());
        holder.ratingTextView.setText(String.format("%.1f", movie.getRating()));
        holder.priceTextView.setText(String.format("%.2f DT", movie.getPrice()));
        // ijib poster
        Glide.with(holder.itemView.getContext())
                .load(movie.getImageResourceId())
                .placeholder(R.drawable.movie_placeholder)
                .into(holder.movieImageView);

        int ageRatingResource = getAgeRatingResource(movie.getAgeRating());
        holder.ageRatingImageView.setImageResource(ageRatingResource);

        holder.itemView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("movieId", movie.getId());
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_movieDetailFragment, args);
        });
    }
    private int getAgeRatingResource(String ageRating) {
        if (ageRating == null)
            return R.drawable.movie_placeholder;
        switch (ageRating.toUpperCase()) {
            case "G":
                return R.drawable.age_rating_all;
            case "PG":
                return R.drawable.age_rating_pg;
            case "PG-13":
                return R.drawable.age_rating_pg13;
            case "R":
                return R.drawable.age_rating_r;
            default:
                return R.drawable.age_rating_all;
        }
    }
    @Override
    public int getItemCount() {
        if (movies != null)
            return movies.size();
        return 0;
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }
    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView movieImageView;
        TextView titleTextView;
        TextView genreTextView;
        TextView descriptionTextView;
        TextView ratingTextView;
        TextView priceTextView;
        ImageView ageRatingImageView;
        MovieViewHolder(View itemView) {
            super(itemView);
            movieImageView = itemView.findViewById(R.id.movieImageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            genreTextView = itemView.findViewById(R.id.genreTextView);
            descriptionTextView = itemView.findViewById(R.id.descriptionTextView);
            ratingTextView = itemView.findViewById(R.id.ratingTextView);
            priceTextView = itemView.findViewById(R.id.priceTextView);
            ageRatingImageView = itemView.findViewById(R.id.ageRatingImageView);
        }
    }
}