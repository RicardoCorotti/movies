package dev.ricardo.movies.infrastructure.api.controller;

import dev.ricardo.movies.application.service.AwardedMoviesService;
import dev.ricardo.movies.infrastructure.api.dto.TopAwardedProducersResponse;
import dev.ricardo.movies.infrastructure.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/awarded-movies")
public class MoviesController {

    @Autowired
    private AwardedMoviesService service;
    @Autowired
    private MovieMapper mapper;

    @GetMapping
    @ResponseBody
    public TopAwardedProducersResponse retrieveAwardedMoviesService() {
        return mapper.toResponse(service.execute());
    }

}
