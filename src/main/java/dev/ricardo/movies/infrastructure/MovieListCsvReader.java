package dev.ricardo.movies.infrastructure;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class MovieListCsvReader {

    private static final Logger log = LoggerFactory.getLogger(MovieListCsvReader.class);

    public List<MovieCsvLine> readMovies(String filePath) {

        ClassPathResource resource = new ClassPathResource(filePath);
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
            log.error("Exception when reading movies file: {}", e.getMessage(), e);
        }
        return movieCsvLines;

    }

}
