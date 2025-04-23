package com.example.cinemaapp.models;

public class Movie {
    private String id;
    private String title;
    private String genre;
    private String description;
    private int imageResourceId;
    private double rating;
    private String ageRating;
    private double price;
    private String trailerUrl;
    private String director;
    private String cast;
    private String releaseDate;
    private int duration;
    public Movie(String id, String title, String genre, String description, int imageResourceId, double rating,
            String ageRating, double price, String trailerUrl, String director, String cast, String releaseDate,
            int duration) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.rating = rating;
        this.ageRating = ageRating;
        this.price = price;
        this.trailerUrl = trailerUrl;
        this.director = director;
        this.cast = cast;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public double getRating() {
        return rating;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public double getPrice() {
        return price;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public String getDirector() {
        return director;
    }

    public String getCast() {
        return cast;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getDuration() {
        return duration;
    }

}