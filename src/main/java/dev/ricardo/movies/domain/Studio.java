package dev.ricardo.movies.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
public class Studio {

    private final UUID id;
    private final String name;

}
