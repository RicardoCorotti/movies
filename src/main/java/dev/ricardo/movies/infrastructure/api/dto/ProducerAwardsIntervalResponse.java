package dev.ricardo.movies.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@JsonPropertyOrder(value = {"producer", "interval", "previousWin", "followingWin"})
public class ProducerAwardsIntervalResponse {

    public ProducerAwardsIntervalResponse(String producer, Integer interval, Integer previousWin, Integer followingWin) {
        this.producer = producer;
        this.interval = interval;
        this.previousWin = previousWin;
        this.followingWin = followingWin;
    }

    @JsonProperty("producer")
    private final String producer;

    @JsonProperty("interval")
    private final Integer interval;

    @JsonProperty("previousWin")
    private final Integer previousWin;

    @JsonProperty("followingWin")
    private final Integer followingWin;

    @Override
    public String toString() {
        return "\nProducer " + producer +
                " - Previous: " + previousWin +
                " - Following: " + followingWin +
                " - Interval: " + interval;
    }

}
