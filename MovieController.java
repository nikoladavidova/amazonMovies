package com.example.demo.amazonMovies;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import java.util.ArrayList;


@RestController
public class MovieController {


    private static List<Movie> loadAllFromFile() throws IOException {

        try (Stream<String> lines = Files.lines(Path.of("amazon_movies.csv"))) {
            return lines
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(";", -1);
                        return new Movie(
                                Integer.parseInt(parts[0]),
                                parts[1],
                                Double.parseDouble(parts[2]),
                                Integer.parseInt(parts[3]),
                                parts[4],
                                Integer.parseInt(parts[5]),
                                parts[6],
                                parts[7],
                                parts[8]
                        );
                    })
                    .toList();
        }
    }

    @GetMapping("/movies")
    public Stream<Movie> getAll(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Double minRating,
            @RequestParam(required = false) Double maxRating,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) String directedBy,
            @RequestParam(required = false) String starring,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber
    ) throws IOException {
        long itemsToSkip = (pageNumber != null && pageSize != null) ? (pageNumber - 1) * pageSize : 0;

        return loadAllFromFile()
                .stream()
                .sorted(Comparator.comparingInt(Movie::getId))
                .filter(movie -> title==null||movie.getTitle().toLowerCase().contains(title.toLowerCase()))
                .filter(movie -> directedBy==null||movie.getDirector().toLowerCase().contains(directedBy.toLowerCase()))
                .filter(movie -> starring==null||movie.getStarring().toLowerCase().contains(starring.toLowerCase()))
                .filter(movie -> minRating == null || movie.getMovieRating() >= minRating )
                .filter(movie -> maxRating == null || movie.getMovieRating() <= maxRating )
                .filter(movie -> minYear == null || movie.getReleaseYear() >= minYear )
                .filter(movie -> maxYear == null || movie.getReleaseYear() <= maxYear )

                .skip(itemsToSkip)
                .limit(pageSize != null ? pageSize : Long.MAX_VALUE);


    }

    @GetMapping("/movies/top")
    public Optional <Movie> getTopMovie() throws IOException {
        return loadAllFromFile()
                .stream()
                .max(Comparator.comparing(Movie::getMovieRating)
                        .thenComparing(Movie::getNoOfRatings));  // nejlepsi film celkov2 podle po4tu hlasu a celkovemu hodnoceni

    }




    @GetMapping("/movies/better-than/{id}")
    public Optional<List<Movie>> getMoviesBetterThan(@PathVariable int id) throws IOException {
        List<Movie> betterRated = new ArrayList<>();


        for (Movie movie : loadAllFromFile()) {
            if (movie.getId() == id) {
                for (Movie betterMovie : loadAllFromFile()) {
                    if (betterMovie.getMovieRating() > movie.getMovieRating()) {
                        betterRated.add(betterMovie);
                    }
                }
                return Optional.of(betterRated); //list
            }
        }

        return null;
    }
}