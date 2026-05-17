package dev.ricardo.movies.domain.gateway;

import dev.ricardo.movies.domain.IAwardedProducer;
import dev.ricardo.movies.domain.Movie;
import dev.ricardo.movies.domain.Producer;

import java.util.List;

public interface MovieGateway {

    public Movie saveMovie(Movie movie);

    public Producer saveProducer(Producer producer);

    public void saveMovieProducer(Movie movie);

    public List<IAwardedProducer> retrieveAwardedProducersOrderedByProducerAndYear();

}
