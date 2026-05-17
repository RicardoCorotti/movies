package dev.ricardo.movies.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder(value = {"producer", "interval", "previousWin", "followingWin"})
public class ProducerAwardsIntervalResponse {

    private final String producer;
    private final Integer interval;
    private final Integer previousWin;
    private final Integer followingWin;

}
