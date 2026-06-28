package dev.ricardo.movies;

import dev.ricardo.movies.application.service.AwardedMoviesService;
import dev.ricardo.movies.application.service.LoadMoviesService;
import dev.ricardo.movies.domain.TopAwardedProducers;
import dev.ricardo.movies.domain.repository.MovieProducerRepository;
import dev.ricardo.movies.domain.repository.MovieRepository;
import dev.ricardo.movies.domain.repository.ProducerRepository;
import dev.ricardo.movies.infrastructure.MovieCsvLine;
import dev.ricardo.movies.infrastructure.MovieListCsvReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
@ActiveProfiles({"default", "integration-test"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration
class MoviesApplicationTests {

    private static final Logger log = LoggerFactory.getLogger(MoviesApplicationTests.class);
    private static final Integer TIMEOUT = 120;

    @Container
    static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres:12")
            .withDatabaseName("testdb")
            .withUsername("sa")
            .withPassword("sa");

    @Autowired
    private MovieListCsvReader reader;

    @Autowired
    private LoadMoviesService loader;

    @Autowired
    private AwardedMoviesService awardedMoviesService;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    ProducerRepository producerRepository;

    @Autowired
    MovieProducerRepository movieProducerRepository;

    private String baseUrl = "http://localhost";

    //@LocalServerPort
    //private int port;

    @BeforeAll
    static void startContainers() {
        Awaitility.await().atMost(Duration.ofSeconds(TIMEOUT)).until(postgres::isRunning);
        log.info("PostgreSQL is up and running");
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {

        log.info("### Port: " + postgres.getFirstMappedPort().toString());

        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    public void testAwardedMovies() {
        assertTrue(postgres.isRunning());

        movieRepository.deleteAllInBatch();
        producerRepository.deleteAllInBatch();
        movieProducerRepository.deleteAllInBatch();

        List<MovieCsvLine> moviesFromCsv = reader.readMovies("/input/Movielist.csv");
        loader.execute(moviesFromCsv);

        TopAwardedProducers topAwardedProducers = awardedMoviesService.execute();

        log.info("TopAwardedProducers Min: " + topAwardedProducers.getMin());
        log.info("TopAwardedProducers Max: " + topAwardedProducers.getMax());

        assertNotNull(topAwardedProducers);

    }
}

