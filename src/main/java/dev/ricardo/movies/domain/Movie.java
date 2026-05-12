package dev.ricardo.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class Movie {

    private final UUID id;
    private final String title;
    private final Integer year;
    private final Boolean winner;

    public static Movie newMovie(String title, Integer year, Boolean winner) {
        return Movie.builder()
            .id(UUID.randomUUID())
            .title(title)
            .year(year)
            .winner(winner)
            .build();
    }

}
