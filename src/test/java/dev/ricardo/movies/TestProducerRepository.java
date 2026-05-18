package dev.ricardo.movies;

import dev.ricardo.movies.domain.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TestProducerRepository extends JpaRepository<ProducerEntity, UUID> {
}
