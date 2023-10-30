package com.example.demo.amazonMovies;

public class Movie {

    private int id;
    private String title;
    private double movieRating;
    private int noOfRatings;
    private String format;
    private int releaseYear;
    private String mpaa;
    private String director;
    private String starring;

    public Movie(int id, String title, double movieRating, int noOfRatings, String format, int releaseYear, String mpaa, String director, String starring) {
        this.id = id;
        this.title = title;
        this.movieRating = movieRating;
        this.noOfRatings = noOfRatings;
        this.format = format;
        this.releaseYear = releaseYear;
        this.mpaa = mpaa;
        this.director = director;
        this.starring = starring;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public int getNoOfRatings() {
        return noOfRatings;
    }

    public String getFormat() {
        return format;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getMpaa() {
        return mpaa;
    }

    public String getDirector() {
        return director;
    }

    public String getStarring() {
        return starring;
    }
}
