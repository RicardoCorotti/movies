package dev.ricardo.movies.infrastructure.gateway;

import dev.ricardo.movies.domain.Movie;
import dev.ricardo.movies.domain.Producer;
import dev.ricardo.movies.domain.entity.MovieEntity;
import dev.ricardo.movies.domain.entity.MovieProducerEntity;
import dev.ricardo.movies.domain.entity.ProducerEntity;
import dev.ricardo.movies.domain.gateway.MovieGateway;
import dev.ricardo.movies.domain.repository.MovieProducerRepository;
import dev.ricardo.movies.domain.repository.MovieRepository;
import dev.ricardo.movies.domain.repository.ProducerRepository;
import dev.ricardo.movies.infrastructure.mapper.MovieMapper;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class MovieGatewayImpl implements MovieGateway {

    private final MovieMapper mapper;
    private final MovieRepository movieRepository;
    private final ProducerRepository producerRepository;
    private final MovieProducerRepository movieProducerRepository;

    public MovieGatewayImpl(
            MovieMapper mapper,
            MovieRepository movieRepository,
            ProducerRepository producerRepository,
            MovieProducerRepository movieProducerRepository) {
        this.mapper = mapper;
        this.movieRepository = movieRepository;
        this.producerRepository = producerRepository;
        this.movieProducerRepository = movieProducerRepository;
    }

    @Override
    public Producer saveProducer(Producer producer) {
        ProducerEntity entity = mapper.domainToEntity(producer);
        entity = producerRepository.save(entity);
        return mapper.entityToDomain(entity);
    }

    @Override
    public Movie saveMovie(Movie movie) {
        MovieEntity entity = mapper.domainToEntity(movie);
        entity = movieRepository.save(entity);
        return mapper.entityToDomain(entity);
    }

    @Override
    public void saveMovieProducer(Movie movie) {
        for (Producer producer: movie.getProducers()) {
            MovieProducerEntity movieProducerEntity = new MovieProducerEntity();
            movieProducerEntity.setMovieProducerId(UUID.randomUUID());
            movieProducerEntity.setMovieId(movie.getMovieId());
            movieProducerEntity.setProducerId(producer.getProducerId());
            movieProducerRepository.save(movieProducerEntity);
        }
    }

}
