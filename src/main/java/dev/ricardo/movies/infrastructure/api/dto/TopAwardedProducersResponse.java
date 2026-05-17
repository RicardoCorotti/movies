package dev.ricardo.movies.infrastructure.api.dto;

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

    List<ProducerAwardsIntervalResponse> min;
    List<ProducerAwardsIntervalResponse> max;

}
