package dev.ricardo.movies.infrastructure.mapper;

import dev.ricardo.movies.domain.Movie;
import dev.ricardo.movies.domain.Producer;
import dev.ricardo.movies.domain.ProducerAwardsInterval;
import dev.ricardo.movies.domain.TopAwardedProducers;
import dev.ricardo.movies.domain.entity.MovieEntity;
import dev.ricardo.movies.domain.entity.ProducerEntity;
import dev.ricardo.movies.infrastructure.api.dto.ProducerAwardsIntervalResponse;
import dev.ricardo.movies.infrastructure.api.dto.TopAwardedProducersResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieEntity domainToEntity(Movie domain);
    Movie entityToDomain(MovieEntity entity);

    ProducerEntity domainToEntity(Producer producer);
    Producer entityToDomain(ProducerEntity entity);

    TopAwardedProducersResponse toResponse(TopAwardedProducers domain);
    ProducerAwardsIntervalResponse toResponse(ProducerAwardsInterval domain);
}
