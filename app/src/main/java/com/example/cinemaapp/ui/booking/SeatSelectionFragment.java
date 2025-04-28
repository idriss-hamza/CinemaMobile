package com.example.cinemaapp.ui.booking;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.content.res.Resources;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.cinemaapp.R;
import com.example.cinemaapp.data.DatabaseHelper;
import com.example.cinemaapp.models.Movie;
import android.app.AlertDialog;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;

public class SeatSelectionFragment extends Fragment {
    private String movieId;
    private String showtime;
    private TextView movieTitleTextView;
    private TextView showtimeTextView;
    private GridLayout seatsGrid;
    private TextView selectedSeatsTextView;
    private TextView totalPriceTextView;
    private Button confirmButton;
    private DatabaseHelper databaseHelper;
    private List<String> selectedSeats = new ArrayList<>();

    private static double SEAT_PRICE ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString("movieId");
            showtime = getArguments().getString("showtime");
        }
        databaseHelper = new DatabaseHelper(requireContext());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_selection, container, false);

        movieTitleTextView = view.findViewById(R.id.movieTitleTextView);
        showtimeTextView = view.findViewById(R.id.showtimeTextView);
        seatsGrid = view.findViewById(R.id.seatsGrid);
        selectedSeatsTextView = view.findViewById(R.id.selectedSeatsTextView);
        totalPriceTextView = view.findViewById(R.id.totalPriceTextView);
        confirmButton = view.findViewById(R.id.confirmButton);
        loadMovieDetails();
        setupSeatsGrid();

        confirmButton.setOnClickListener(v -> confirmBooking());
        return view;
    }
    private void loadMovieDetails() {
        Movie movie = databaseHelper.getMovieById(movieId);
        if (movie != null) {
            movieTitleTextView.setText(movie.getTitle());
            SEAT_PRICE=movie.getPrice();
            showtimeTextView.setText("Showtime: " + showtime);
        }
    }

    private void setupSeatsGrid() {
        seatsGrid.removeAllViews();
        Resources res = requireContext().getResources();
        int seatWidth = res.getDimensionPixelSize(R.dimen.seat_width);
        int seatHeight = res.getDimensionPixelSize(R.dimen.seat_height);
        int seatMargin = res.getDimensionPixelSize(R.dimen.seat_margin);
        int seatPadding = res.getDimensionPixelSize(R.dimen.seat_padding);

        Set<String> reservedSeats = getReservedSeats();

        for (int row = 0; row < 8; row++) {
            TextView rowLabel = new TextView(requireContext());
            rowLabel.setText(String.valueOf((char) ('A' + row)));
            rowLabel.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = seatWidth;
            params.height = seatHeight;
            params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);
            params.rowSpec = GridLayout.spec(row + 1);
            params.columnSpec = GridLayout.spec(0);
            seatsGrid.addView(rowLabel, params);
        }
        for (int col = 0; col < 9; col++) {
            TextView colLabel = new TextView(requireContext());
            colLabel.setText(String.valueOf(col + 1));
            colLabel.setGravity(Gravity.CENTER);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = seatWidth;
            params.height = seatHeight;
            params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);
            params.rowSpec = GridLayout.spec(0);
            params.columnSpec = GridLayout.spec(col + 1);
            seatsGrid.addView(colLabel, params);
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 9; col++) {
                Button seatButton = new Button(requireContext());
                seatButton.setBackgroundResource(R.drawable.seat_button_background);
                seatButton.setPadding(seatPadding, seatPadding, seatPadding, seatPadding);
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = seatWidth;
                params.height = seatHeight;
                params.setMargins(seatMargin, seatMargin, seatMargin, seatMargin);
                params.rowSpec = GridLayout.spec(row + 1);
                params.columnSpec = GridLayout.spec(col + 1);
                final String seatId = String.format("%c%d", (char) ('A' + row), col + 1);
                seatButton.setTag(seatId);
                if (reservedSeats.contains(seatId)) {
                    seatButton.setEnabled(false);
                    seatButton.setText("");
                    seatButton.setTextColor(Color.WHITE);
                } else {
                    seatButton.setOnClickListener(v -> {
                        if (selectedSeats.contains(seatId)) {
                            selectedSeats.remove(seatId);
                            v.setSelected(false);
                        } else {
                            selectedSeats.add(seatId);
                            v.setSelected(true);
                        }
                        updateSelectedSeatsAndPrice();
                    });
                }
                seatsGrid.addView(seatButton, params);
            }
        }
    }
    private Set<String> getReservedSeats() {
        Set<String> reservedSeats = new HashSet<>();
        Cursor cursor = null;
        try {
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            String[] columns = { DatabaseHelper.SEATS};
            String selection = DatabaseHelper.MOVIE_ID + " = ? AND " +
                    DatabaseHelper.SHOWTIME + " = ?";
            String[] selectionArgs = { movieId, showtime };
            cursor = db.query(DatabaseHelper.RESERVATIONS, columns, selection,
                    selectionArgs, null, null, null);
            while (cursor != null && cursor.moveToNext()) {
                String seats = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.SEATS));
                if (seats != null) {
                    reservedSeats.addAll(Arrays.asList(seats.split(",")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return reservedSeats;
    }
    private void updateSelectedSeatsAndPrice() {
        if (selectedSeats.isEmpty()) {
            selectedSeatsTextView.setText("No seats selected");
            totalPriceTextView.setText("Total: 0.00 DT");
        } else {
            selectedSeatsTextView.setText("Selected seats: " + String.join(", ", selectedSeats));
            double totalPrice = selectedSeats.size() * SEAT_PRICE;
            totalPriceTextView.setText(String.format("Total: %.2f DT", totalPrice));
        }
    }
    private void confirmBooking() {
        if (selectedSeats.isEmpty()) {
            Toast.makeText(requireContext(), "Please select at least one seat", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isAdded()) {
            return;
        }

        View dialogView = getLayoutInflater().inflate(R.layout.dialog_reservation_form, null);
        AlertDialog dialog = new AlertDialog.Builder(requireContext())
                .setView(dialogView)
                .setCancelable(false)
                .create();

        TextInputEditText nameEditText = dialogView.findViewById(R.id.nameEditText);
        TextInputEditText surnameEditText = dialogView.findViewById(R.id.surnameEditText);
        TextInputEditText emailEditText = dialogView.findViewById(R.id.emailEditText);
        TextInputEditText addressEditText = dialogView.findViewById(R.id.telephoneEditText);
        Button cancelButton = dialogView.findViewById(R.id.cancelButton);
        Button confirmButton = dialogView.findViewById(R.id.confirmButton);
        cancelButton.setOnClickListener(v -> {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        });

        confirmButton.setOnClickListener(v -> {
            if (!isAdded()) {
                dialog.dismiss();
                return;
            }
            String name = nameEditText.getText().toString().trim();
            String surname = surnameEditText.getText().toString().trim();
            String email = emailEditText.getText().toString().trim();
            String address = addressEditText.getText().toString().trim();

            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isValidEmail(email)) {
                Toast.makeText(requireContext(), "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                return;
            }

            saveReservation(name, surname, email, address);
            dialog.dismiss();
        });

        if (isAdded()) {
            dialog.show();
        }
    }
    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    private void saveReservation(String name, String surname, String email, String address) {
        if (!isAdded()) {
            return;
        }
        double totalPrice = selectedSeats.size() * SEAT_PRICE;

        boolean success = databaseHelper.saveReservation(
                email, name, surname, address,
                movieId, showtime, selectedSeats,
                totalPrice);

        if (success) {
            Toast.makeText(requireContext(), "Reservation confirmed!", Toast.LENGTH_SHORT).show();
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    getActivity().onBackPressed();
                });
            }
        } else {
            Toast.makeText(requireContext(),
                    "Failed to save reservation. Please try again.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}