package com.example.cinemaapp.ui.movie;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import com.example.cinemaapp.R;
import java.util.Arrays;
import java.util.List;

public class TimeSelectionDialog extends DialogFragment {
    private static final String MOVIE_ID = "movieId";
    private String movieId;
    private OnTimeSelectedListener listener;
    public interface OnTimeSelectedListener {
        void onTimeSelected(String showtime);
    }
    public static TimeSelectionDialog newInstance(String movieId) {
        TimeSelectionDialog dialog = new TimeSelectionDialog();
        Bundle args = new Bundle();
        args.putString(MOVIE_ID, movieId);
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(MOVIE_ID);
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_time_selection, null);

        TextView titleTextView = view.findViewById(R.id.dialogTitleTextView);
        titleTextView.setText("Select Showtime For Today");
        GridLayout timeGrid = view.findViewById(R.id.timeGridLayout);
        List<String> showtimes = Arrays.asList("10:00 AM", "1:00 PM", "4:00 PM", "7:00 PM", "10:00 PM");
        for (String time : showtimes) {
            Button timeButton = new Button(requireContext());
            timeButton.setText(time);
            timeButton.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTimeSelected(time);
                }
                dismiss();
            });
            timeGrid.addView(timeButton);
        }
        builder.setView(view);
        return builder.create();
    }
    public void setOnTimeSelectedListener(OnTimeSelectedListener listener) {
        this.listener = listener;
    }
}