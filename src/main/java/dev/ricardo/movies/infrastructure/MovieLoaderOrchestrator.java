package dev.ricardo.movies.infrastructure;

import dev.ricardo.movies.application.service.LoadMoviesService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieLoaderOrchestrator {

    private final MovieListCsvReader reader;
    private final LoadMoviesService loader;

    public MovieLoaderOrchestrator(
            MovieListCsvReader reader,
            LoadMoviesService loader) {
        this.reader = reader;
        this.loader = loader;
    }

    @PostConstruct
    public void process() {
        List<MovieCsvLine> moviesFromCsv = reader.readMovies();
        loader.execute(moviesFromCsv);
    }

}
