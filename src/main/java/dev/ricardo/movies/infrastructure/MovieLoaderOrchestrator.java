package dev.ricardo.movies.infrastructure;

import dev.ricardo.movies.application.LoadMoviesUseCaseImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieLoaderOrchestrator {

    private final MovieListCsvReader reader;
    private final LoadMoviesUseCaseImpl loader;

    public MovieLoaderOrchestrator(
            MovieListCsvReader reader,
            LoadMoviesUseCaseImpl loader) {
        this.reader = reader;
        this.loader = loader;
    }

    @PostConstruct
    public void process() {
        List<MovieCsvLine> moviesFromCsv = reader.readMovies();
        loader.loadMovies(moviesFromCsv);
    }

}
