package dev.ricardo.movies.infrastructure.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonPropertyOrder(value = {"min", "max"})
public class TopAwardedProducersResponse {

    public TopAwardedProducersResponse(List<ProducerAwardsIntervalResponse> min, List<ProducerAwardsIntervalResponse> max) {
        this.min = min;
        this.max = max;
    }

    @JsonProperty("min")
    List<ProducerAwardsIntervalResponse> min;

    @JsonProperty("max")
    List<ProducerAwardsIntervalResponse> max;

}
