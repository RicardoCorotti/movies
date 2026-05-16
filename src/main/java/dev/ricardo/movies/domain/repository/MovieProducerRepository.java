package dev.ricardo.movies.domain.repository;

import dev.ricardo.movies.domain.entity.MovieProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovieProducerRepository extends JpaRepository<MovieProducerEntity, UUID> {
}
