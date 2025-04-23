package com.example.cinemaapp.ui.movie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.DatabaseHelper;
import com.example.cinemaapp.models.Movie;

public class MovieDetailFragment extends Fragment {
    private String movieId;
    private ImageView moviePosterImageView;
    private TextView movieTitleTextView;
    private TextView genreTextView;
    private TextView ratingTextView;
    private TextView descriptionTextView;
    private TextView priceTextView;
    private TextView directorTextView;
    private TextView castTextView;
    private TextView releaseDateTextView;
    private TextView durationTextView;
    private ImageView ageRatingImageView;
    private Button bookTicketButton;
    private Button watchTrailerButton;
    private DatabaseHelper databaseHelper;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString("movieId");
        }
        databaseHelper = new DatabaseHelper(requireContext());
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        moviePosterImageView = view.findViewById(R.id.moviePosterImageView);
        movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
        genreTextView = view.findViewById(R.id.genreTextView);
        ratingTextView = view.findViewById(R.id.ratingTextView);
        descriptionTextView = view.findViewById(R.id.descriptionTextView);
        priceTextView = view.findViewById(R.id.priceTextView);
        directorTextView = view.findViewById(R.id.directorTextView);
        castTextView = view.findViewById(R.id.castTextView);
        releaseDateTextView = view.findViewById(R.id.releaseDateTextView);
        durationTextView = view.findViewById(R.id.durationTextView);
        ageRatingImageView = view.findViewById(R.id.ageRatingImageView);
        bookTicketButton = view.findViewById(R.id.bookTicketButton);
        watchTrailerButton = view.findViewById(R.id.watchTrailerButton);

        loadMovieDetails();
        bookTicketButton.setOnClickListener(v -> showTimeSelectionDialog());
        watchTrailerButton.setOnClickListener(v -> openTrailer());
        return view;
    }

    private void openTrailer() {
        Movie movie = databaseHelper.getMovieById(movieId);
        if (movie != null && movie.getTrailerUrl() != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
            startActivity(intent);
        }
    }
    private void loadMovieDetails() {
        Movie movie = databaseHelper.getMovieById(movieId);
        if (movie != null) {
            movieTitleTextView.setText(movie.getTitle());
            genreTextView.setText(movie.getGenre());
            ratingTextView.setText(String.format("%.1f", movie.getRating()));
            descriptionTextView.setText(movie.getDescription());
            priceTextView.setText(String.format("%.2f DT", movie.getPrice()));
            directorTextView.setText(movie.getDirector());
            castTextView.setText(movie.getCast());
            releaseDateTextView.setText(movie.getReleaseDate());
            durationTextView.setText(String.format("%d minutes", movie.getDuration()));

            //poster
            Glide.with(this)
                    .load(movie.getImageResourceId())
                    .placeholder(R.drawable.movie_placeholder)
                    .into(moviePosterImageView);

            int ageRatingResource = getAgeRatingResource(movie.getAgeRating());
            ageRatingImageView.setImageResource(ageRatingResource);
        }
    }
    private void showTimeSelectionDialog() {
        TimeSelectionDialog dialog = TimeSelectionDialog.newInstance(movieId);
        dialog.setOnTimeSelectedListener(showtime -> {
            Bundle args = new Bundle();
            args.putString("movieId", movieId);
            args.putString("showtime", showtime);
            Navigation.findNavController(requireView())
                    .navigate(R.id.action_movieDetailFragment_to_seatSelectionFragment, args);
        });
        dialog.show(getChildFragmentManager(), "timeSelection");
    }
    private int getAgeRatingResource(String ageRating) {
        switch (ageRating.toLowerCase()) {
            case "g":
                return R.drawable.age_rating_all;
            case "pg":
                return R.drawable.age_rating_pg;
            case "pg-13":
                return R.drawable.age_rating_pg13;
            case "r":
                return R.drawable.age_rating_r;
            default:
                return R.drawable.age_rating_all;
        }
    }
}