package dev.ricardo.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class TopAwardedProducers {

    List<ProducerAwardsInterval> min;
    List<ProducerAwardsInterval> max;

}
