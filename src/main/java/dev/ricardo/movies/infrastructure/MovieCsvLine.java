package dev.ricardo.movies.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class MovieCsvLine {

    private final String year;
    private final String title;
    private final String studios;
    private final String producers;
    private final String winner;

    public static MovieCsvLine fromFieldArray(String[] fields) {
        return MovieCsvLine.builder()
            .year(fields[0])
            .title(fields[1])
            .studios(fields[2])
            .producers(fields[3])
            .winner(fields[4])
            .build();
    }

}
