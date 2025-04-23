package com.example.cinemaapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinemaapp.R;
import com.example.cinemaapp.adapters.MovieAdapter;
import com.example.cinemaapp.data.DatabaseHelper;
import com.example.cinemaapp.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> allMovies;
    private DatabaseHelper databaseHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        databaseHelper = new DatabaseHelper(requireContext());
        recyclerView = view.findViewById(R.id.moviesRecyclerView);
        SearchView searchView = view.findViewById(R.id.searchView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new MovieAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);
        loadMovies();

        //recherche
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterMovies(query);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filterMovies(newText);
                return true;
            }
        });
        return view;
    }
    private void loadMovies() {
        allMovies = databaseHelper.getAllMovies();
        adapter.setMovies(allMovies);
    }
    private void filterMovies(String query) {
        if (allMovies == null)
            return;
        List<Movie> filteredList = new ArrayList<>();
        for (Movie movie : allMovies) {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                    movie.getGenre().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(movie);
            }
        }
        adapter.setMovies(filteredList);
    }
}