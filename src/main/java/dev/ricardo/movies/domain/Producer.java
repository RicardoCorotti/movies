package dev.ricardo.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter
@Setter
public class Producer {

    private final UUID producerId;
    private final String name;

    public static Producer newProducer(String name) {
        return Producer.builder()
            .producerId(UUID.randomUUID())
            .name(name)
            .build();
    }

}
