package dev.ricardo.movies.domain.repository;

import dev.ricardo.movies.application.dto.WinnerDTO;
import dev.ricardo.movies.domain.entity.ProducerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProducerRepository extends JpaRepository<ProducerEntity, UUID> {

    @Query(value = "select new dev.ricardo.movies.application.dto.WinnerDTO(p.name, m.release_year, m.title) " +
        "from producer p " +
        "join movie_producer mp on p.producer_id = mp.producer_id " +
        "join movie m on mp.movie_id = m.movie_id " +
        "where m.winner is true " +
        "order by p.name, m.release_year"
    , nativeQuery = true)
    List<WinnerDTO> listWinnerMoviesOrderedByProducerAndYear();

}
