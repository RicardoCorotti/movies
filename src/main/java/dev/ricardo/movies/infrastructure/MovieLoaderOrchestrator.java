package dev.ricardo.movies.infrastructure;

import dev.ricardo.movies.application.service.LoadMoviesService;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
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

    @EventListener(ApplicationReadyEvent.class)
    public void process() {
        List<MovieCsvLine> moviesFromCsv = reader.readMovies("/input/Movielist.csv");
        loader.execute(moviesFromCsv);
    }

}
