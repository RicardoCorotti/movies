package dev.ricardo.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProducerAwardsInterval {

    private final String producer;
    private final Integer interval;
    private final Integer previousWin;
    private final Integer followingWin;

    @Override
    public String toString() {
        return "\nProducer " + producer +
                " - Previous: " + previousWin +
                " - Following: " + followingWin +
                " - Interval: " + interval;
    }

}
