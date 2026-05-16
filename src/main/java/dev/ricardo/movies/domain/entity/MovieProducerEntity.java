package dev.ricardo.movies.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="movie_producer")
@Getter
@Setter
public class MovieProducerEntity {

    @Id
    @Column(name = "movie_producer_id")
    private UUID movieProducerId;

    @Column(name = "movie_id")
    private UUID movieId;

    @Column(name = "producer_id")
    private UUID producerId;

}
