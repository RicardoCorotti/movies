package dev.ricardo.movies.domain.gateway;

import dev.ricardo.movies.domain.Movie;
import dev.ricardo.movies.domain.Producer;

public interface MovieGateway {

    public Movie saveMovie(Movie movie);

    public Producer saveProducer(Producer producer);

    public void saveMovieProducer(Movie movie);

}
