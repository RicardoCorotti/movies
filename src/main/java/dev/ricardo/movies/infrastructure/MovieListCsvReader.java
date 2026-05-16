package dev.ricardo.movies.infrastructure;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieListCsvReader {

    public List<MovieCsvLine> readMovies() {

        ClassPathResource resource = new ClassPathResource("/input/Movielist.csv");
        List<MovieCsvLine> movieCsvLines = new ArrayList<>();
        try (InputStream inputStream = resource.getInputStream()) {
            CSVParser csvParser = new CSVParserBuilder().withSeparator(';').build();
            CSVReader csvReader = new CSVReaderBuilder(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .withCSVParser(csvParser)
                    .withSkipLines(1)
                    .build();
            String[] fieldsFromCsvLine;
            while ((fieldsFromCsvLine = csvReader.readNext()) != null) {
                MovieCsvLine movieCsvLine = MovieCsvLine.fromFieldArray(fieldsFromCsvLine);
                movieCsvLines.add(movieCsvLine);
            }
        } catch (Exception e) {
            System.out.println("Erro");
        }
        return movieCsvLines;

    }

}
