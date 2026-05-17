package dev.ricardo.movies.application.service;

import dev.ricardo.movies.domain.Movie;
import dev.ricardo.movies.domain.Producer;
import dev.ricardo.movies.domain.gateway.MovieGateway;
import dev.ricardo.movies.infrastructure.MovieCsvLine;
import dev.ricardo.movies.infrastructure.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class LoadMoviesService {

    private final MovieMapper mapper;
    private final MovieGateway gateway;

    @Autowired
    public LoadMoviesService(MovieMapper mapper, MovieGateway gateway) {
        this.mapper = mapper;
        this.gateway = gateway;
    }

    public void execute(List<MovieCsvLine> movieCsvLines) {
        Map<String, Producer> distinctProducers = new HashMap<>();
        for (MovieCsvLine csvLine: movieCsvLines) {
            List<String> studiosNamesList = getTokens(csvLine.getStudios());
            List<String> producerNames = getTokens(csvLine.getProducers());
            Movie movie = Movie.newMovie(
                    csvLine.getTitle(),
                    Integer.valueOf(csvLine.getYear()),
                    "yes".equals(csvLine.getWinner()));
            movie = gateway.saveMovie(movie);
            for (String producerName: producerNames) {
                Producer producer = distinctProducers.get(producerName);
                if (Objects.isNull(producer)) {
                    producer = Producer.newProducer(producerName);
                    distinctProducers.put(producerName, producer);
                }
                producer = gateway.saveProducer(producer);
                movie.addProducer(producer);
            }
            gateway.saveMovieProducer(movie);
        }
    }

    private List<String> getTokens(String str) {
        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(str, ",");
        while (tokenizer.hasMoreElements()) {
            String token = tokenizer.nextToken();
            if (token.startsWith(" and ")) {
                tokens.add(token.substring(5));
            } else if (token.contains(" and ")) {
                int andPosition = token.indexOf(" and ");
                String leftToken = token.substring(0, andPosition);
                String rightToken = token.substring(andPosition + 4);
                tokens.add(leftToken.trim());
                tokens.add(rightToken.trim());
            } else {
                tokens.add(token.trim());
            }
        }
        return tokens;
    }

}
