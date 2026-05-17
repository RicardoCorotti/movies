package dev.ricardo.movies.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name="movie")
@Getter
@Setter
public class MovieEntity {

    @Id
    @Column(name = "movie_id")
    private UUID movieId;

    @Column(name="title", length=100, nullable=false)
    private String title;

    @Column(name="ref_year", nullable=false)
    private Integer year;

    @Column(name="winner")
    private Boolean winner;

}
