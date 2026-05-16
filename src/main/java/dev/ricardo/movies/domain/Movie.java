package dev.ricardo.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Movie {

    private final UUID movieId;
    private final String title;
    private final Integer releaseYear;
    private final Boolean winner;
    private Set<Producer> producers;

    public static Movie newMovie(String title, Integer releaseYear, Boolean winner) {
        return Movie.builder()
            .movieId(UUID.randomUUID())
            .title(title)
            .releaseYear(releaseYear)
            .winner(winner)
            .producers(new HashSet<>())
            .build();
    }

    public void addProducer(Producer producer) {
        if (Objects.isNull(producers)) {
            producers = new HashSet<>();
        }
        producers.add(producer);
    }

}
