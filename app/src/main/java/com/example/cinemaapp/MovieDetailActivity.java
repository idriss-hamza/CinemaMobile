package com.example.cinemaapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.HashSet;
import java.util.Set;

public class MovieDetailActivity extends AppCompatActivity {
    private GridLayout seatGrid;
    private TextView selectedSeatsTextView;
    private TextView totalPriceTextView;
    private Button bookButton;
    private Set<Integer> selectedSeats = new HashSet<>();
    private static final int PRICE_PER_SEAT = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        String movieTitle = getIntent().getStringExtra("movie_title");
        TextView titleTextView = findViewById(R.id.movieTitleTextView);
        titleTextView.setText(movieTitle);
        seatGrid = findViewById(R.id.seatGrid);
        selectedSeatsTextView = findViewById(R.id.selectedSeatsTextView);
        totalPriceTextView = findViewById(R.id.totalPriceTextView);
        bookButton = findViewById(R.id.bookButton);
        setupSeatGrid();
        setupBookButton();
    }
    private void setupSeatGrid() {
        for (int i = 0; i < 64; i++) {
            View seatView = getLayoutInflater().inflate(R.layout.item_seat, seatGrid, false);
            final int seatNumber = i + 1;
            seatView.setOnClickListener(v -> toggleSeat(seatView, seatNumber));
            seatGrid.addView(seatView);
        }
    }
    private void toggleSeat(View seatView, int seatNumber) {
        if (selectedSeats.contains(seatNumber)) {
            selectedSeats.remove(seatNumber);
            seatView.setBackgroundResource(R.drawable.seat_available);
        } else {
            selectedSeats.add(seatNumber);
            seatView.setBackgroundResource(R.drawable.seat_selected);
        }
        updateSelectedSeatsText();
        updateTotalPrice();
    }
    private void updateSelectedSeatsText() {
        StringBuilder seatsText = new StringBuilder("Selected seats: ");
        for (int seat : selectedSeats) {
            seatsText.append(seat).append(", ");
        }
        if (seatsText.length() > 14) {
            seatsText.setLength(seatsText.length() - 2);
        }
        selectedSeatsTextView.setText(seatsText.toString());
    }
    private void updateTotalPrice() {
        int totalPrice = selectedSeats.size() * PRICE_PER_SEAT;
        totalPriceTextView.setText("Total price: " + totalPrice +" DT");
    }
    private void setupBookButton() {
        bookButton.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(this, "Booking successful!", Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}