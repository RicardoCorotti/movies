package dev.ricardo.movies.domain.repository;

import dev.ricardo.movies.domain.IAwardedProducer;
import dev.ricardo.movies.domain.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, UUID> {

    @Query(value = "select p.name as producerName, m.ref_year as refYear, m.title as movieTitle " +
        "from producer p " +
        "join movie_producer mp on p.producer_id = mp.producer_id " +
        "join movie m on mp.movie_id = m.movie_id " +
        "where m.winner is true " +
        "and (select count(*) " +
             "from movie_producer mult_winner " +
             "join movie m2 on mult_winner.movie_id = m2.movie_id " +
             "where mult_winner.producer_id = mp.producer_id " +
             "and m2.winner is true) >= 2 " +
        "order by p.name, m.ref_year"
    , nativeQuery = true)
    public List<IAwardedProducer> retrieveAwardedProducersOrderedByProducerAndYear();

}
