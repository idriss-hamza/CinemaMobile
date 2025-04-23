package com.example.cinemaapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.cinemaapp.models.Movie;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String MOVIES = "movies";
    public static final String RESERVATIONS = "reservations";
    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String GENRE = "genre";
    public static final String DESCRIPTION = "description";
    public static final String IMAGE_ID = "image_resource_id";
    public static final String RATING = "rating";
    public static final String AGE_RATING = "age_rating";
    public static final String PRICE = "price";
    public static final String TRAILER_URL = "trailer_url";
    public static final String DIRECTOR = "director";
    public static final String CAST = "cast";
    public static final String RELEASE_DATE = "release_date";
    public static final String DURATION = "duration";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String TEL = "tel";
    public static final String MOVIE_ID = "movie_id";
    public static final String SHOWTIME = "showtime";
    public static final String SEATS = "seats";
    public static final String TOTAL_PRICE = "total_price";
    public static final String RESERVATION_DATE = "reservation_date";

    private static final String CREATE_TABLE_MOVIES = "CREATE TABLE " + MOVIES + "("
            + ID + " TEXT PRIMARY KEY,"
            + TITLE + " TEXT,"
            + GENRE + " TEXT,"
            + DESCRIPTION + " TEXT,"
            + IMAGE_ID + " INTEGER,"
            + RATING + " REAL,"
            + AGE_RATING + " TEXT,"
            + PRICE + " REAL,"
            + TRAILER_URL + " TEXT,"
            + DIRECTOR + " TEXT,"
            + CAST + " TEXT,"
            + RELEASE_DATE + " TEXT,"
            + DURATION + " INTEGER"
            + ")";

    public DatabaseHelper(Context context) {
        super(context, "cinema_app.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MOVIES);
        insertInitialMovies(db);
        String CREATE_RESERVATIONS_TABLE = "CREATE TABLE " + RESERVATIONS + "("
                + EMAIL + " TEXT PRIMARY KEY,"
                + NAME + " TEXT NOT NULL,"
                + SURNAME + " TEXT NOT NULL,"
                + TEL + " TEXT NOT NULL,"
                + MOVIE_ID + " TEXT NOT NULL,"
                + SHOWTIME + " TEXT NOT NULL,"
                + SEATS + " TEXT NOT NULL,"
                + TOTAL_PRICE + " REAL NOT NULL,"
                + RESERVATION_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_RESERVATIONS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MOVIES);
        db.execSQL("DROP TABLE IF EXISTS " + RESERVATIONS);
        onCreate(db);
    }

    private void insertInitialMovies(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            insertMovie(db, new Movie(
                    "1",
                    "The Dark Knight",
                    "Action, Crime, Drama",
                    "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                    android.R.drawable.ic_menu_gallery,
                    4.8,
                    "PG-13",
                    12.99,
                    "https://www.youtube.com/watch?v=EXeTwQWrcwY",
                    "Christopher Nolan",
                    "Christian Bale, Heath Ledger, Aaron Eckhart, Michael Caine",
                    "July 18, 2008",
                    152));
            insertMovie(db, new Movie(
                    "2",
                    "Inception",
                    "Action, Sci-Fi, Thriller",
                    "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                    android.R.drawable.ic_menu_gallery,
                    4.7,
                    "PG-13",
                    11.99,
                    "https://www.youtube.com/watch?v=YoHD9XEInc0",
                    "Christopher Nolan",
                    "Leonardo DiCaprio, Joseph Gordon-Levitt, Ellen Page, Tom Hardy",
                    "July 16, 2010",
                    148));
            insertMovie(db, new Movie(
                    "3",
                    "Interstellar",
                    "Adventure, Drama, Sci-Fi",
                    "A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.",
                    android.R.drawable.ic_menu_gallery,
                    4.9,
                    "PG-13",
                    13.99,
                    "https://www.youtube.com/watch?v=zSWdZVtXT7E",
                    "Christopher Nolan",
                    "Matthew McConaughey, Anne Hathaway, Jessica Chastain, Michael Caine",
                    "November 7, 2014",
                    169));
            insertMovie(db, new Movie(
                    "4",
                    "The Matrix",
                    "Action, Sci-Fi",
                    "A computer programmer discovers that reality as he knows it is a simulation created by machines, and joins a rebellion to break free.",
                    android.R.drawable.ic_menu_gallery,
                    4.8,
                    "R",
                    10.99,
                    "https://www.youtube.com/watch?v=vKQi3bBA1y8",
                    "Lana Wachowski, Lilly Wachowski",
                    "Keanu Reeves, Laurence Fishburne, Carrie-Anne Moss, Hugo Weaving",
                    "March 31, 1999",
                    136));
            insertMovie(db, new Movie(
                    "5",
                    "Pulp Fiction",
                    "Crime, Drama",
                    "Various interconnected stories of criminals in Los Angeles.",
                    android.R.drawable.ic_menu_gallery,
                    4.7,
                    "R",
                    9.99,
                    "https://www.youtube.com/watch?v=s7EdQ4FqbhY",
                    "Quentin Tarantino",
                    "John Travolta, Samuel L. Jackson, Uma Thurman, Bruce Willis",
                    "October 14, 1994",
                    154));
            insertMovie(db, new Movie(
                    "0",
                    "Sahbek Rajel",
                    "Comedy, Adventure, Action",
                    "Azouz and Mahdi, two opposite cops, clash through action and comedy as their partnership sparks chaos and unexpected friendship.",
                    android.R.drawable.ic_menu_gallery,
                    4.9,
                    "PG-13",
                    16,
                    "https://www.youtube.com/watch?v=mGYNGk0O0PM&ab_channel=GoubantiniCin%C3%A9maetSpectacles",
                    "Kais Chekir",
                    "Karim Al Gharbi, Yassine Ben Gamra, Dorra Zarrouk",
                    "Dec 21 2024",
                    135));
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void insertMovie(SQLiteDatabase db, Movie movie) {
        ContentValues values = new ContentValues();
        values.put(ID, movie.getId());
        values.put(TITLE, movie.getTitle());
        values.put(GENRE, movie.getGenre());
        values.put(DESCRIPTION, movie.getDescription());
        values.put(IMAGE_ID, movie.getImageResourceId());
        values.put(RATING, movie.getRating());
        values.put(AGE_RATING, movie.getAgeRating());
        values.put(PRICE, movie.getPrice());
        values.put(TRAILER_URL, movie.getTrailerUrl());
        values.put(DIRECTOR, movie.getDirector());
        values.put(CAST, movie.getCast());
        values.put(RELEASE_DATE, movie.getReleaseDate());
        values.put(DURATION, movie.getDuration());
        db.insert(MOVIES, null, values);
    }

    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + MOVIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie(
                        cursor.getString(cursor.getColumnIndex(ID)),
                        cursor.getString(cursor.getColumnIndex(TITLE)),
                        cursor.getString(cursor.getColumnIndex(GENRE)),
                        cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndex(IMAGE_ID)),
                        cursor.getDouble(cursor.getColumnIndex(RATING)),
                        cursor.getString(cursor.getColumnIndex(AGE_RATING)),
                        cursor.getDouble(cursor.getColumnIndex(PRICE)),
                        cursor.getString(cursor.getColumnIndex(TRAILER_URL)),
                        cursor.getString(cursor.getColumnIndex(DIRECTOR)),
                        cursor.getString(cursor.getColumnIndex(CAST)),
                        cursor.getString(cursor.getColumnIndex(RELEASE_DATE)),
                        cursor.getInt(cursor.getColumnIndex(DURATION)));
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return movies;
    }

    public Movie getMovieById(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(MOVIES, null,
                ID + "=?", new String[] { id },
                null, null, null);

        Movie movie = null;
        if (cursor.moveToFirst()) {
            movie = new Movie(
                    cursor.getString(cursor.getColumnIndex(ID)),
                    cursor.getString(cursor.getColumnIndex(TITLE)),
                    cursor.getString(cursor.getColumnIndex(GENRE)),
                    cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                    cursor.getInt(cursor.getColumnIndex(IMAGE_ID)),
                    cursor.getDouble(cursor.getColumnIndex(RATING)),
                    cursor.getString(cursor.getColumnIndex(AGE_RATING)),
                    cursor.getDouble(cursor.getColumnIndex(PRICE)),
                    cursor.getString(cursor.getColumnIndex(TRAILER_URL)),
                    cursor.getString(cursor.getColumnIndex(DIRECTOR)),
                    cursor.getString(cursor.getColumnIndex(CAST)),
                    cursor.getString(cursor.getColumnIndex(RELEASE_DATE)),
                    cursor.getInt(cursor.getColumnIndex(DURATION)));
        }
        cursor.close();
        return movie;
    }

    public boolean saveReservation(String email, String name, String surname, String address,
            String movieId, String showtime, List<String> selectedSeats,
            double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(EMAIL, email);
        values.put(NAME, name);
        values.put(SURNAME, surname);
        values.put(TEL, address);
        values.put(MOVIE_ID, movieId);
        values.put(SHOWTIME, showtime);
        values.put(SEATS, String.join(",", selectedSeats));
        values.put(TOTAL_PRICE, totalPrice);

        long result = db.insertWithOnConflict(RESERVATIONS, null, values,
                SQLiteDatabase.CONFLICT_REPLACE);

        return result != -1;
    }

}