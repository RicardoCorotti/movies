package dev.ricardo.movies.application;

import dev.ricardo.movies.infrastructure.MovieCsvLine;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Component
public class LoadMoviesUseCaseImpl {

    public void loadMovies(List<MovieCsvLine> movieCsvLines) {
        for (MovieCsvLine movieCsvLine: movieCsvLines) {
            List<String> studiosNamesList = getTokens(movieCsvLine.getStudios());
            List<String> producersNamesList = getTokens(movieCsvLine.getProducers());
            System.out.println(movieCsvLine.getTitle());
            for (String producer: producersNamesList) {
                System.out.println(producer);
            }
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
