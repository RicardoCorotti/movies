package dev.ricardo.movies;

import dev.ricardo.movies.application.service.AwardedMoviesService;
import dev.ricardo.movies.application.service.LoadMoviesService;
import dev.ricardo.movies.domain.repository.MovieProducerRepository;
import dev.ricardo.movies.domain.repository.MovieRepository;
import dev.ricardo.movies.domain.repository.ProducerRepository;
import dev.ricardo.movies.infrastructure.MovieCsvLine;
import dev.ricardo.movies.infrastructure.MovieListCsvReader;
import dev.ricardo.movies.infrastructure.api.dto.ProducerAwardsIntervalResponse;
import dev.ricardo.movies.infrastructure.api.dto.TopAwardedProducersResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.shaded.org.awaitility.Awaitility;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    private static String baseUrl = "http://localhost";

    @LocalServerPort
    private int port;

    private static RestTemplate restTemplate;

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

    @BeforeEach
    void setup() {
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/awarded-movies");
        restTemplate = new RestTemplate();
    }

    @Test
    public void testAwardedMovies() {
        assertTrue(postgres.isRunning());

        movieRepository.deleteAllInBatch();
        producerRepository.deleteAllInBatch();
        movieProducerRepository.deleteAllInBatch();

        List<MovieCsvLine> moviesFromCsv = reader.readMovies("/input/MovielistTestesIntegrados.csv");
        loader.execute(moviesFromCsv);

        TopAwardedProducersResponse response = restTemplate.getForEntity(baseUrl, TopAwardedProducersResponse.class).getBody();


        log.info("TopAwardedProducers Min: " + response.getMin());
        log.info("TopAwardedProducers Max: " + response.getMax());

        assertNotNull(response, "O serviço não está retornando uma resposta válida");
        assertNotNull(response.getMin(), "O serviço não está retornando o produtor com menor intervalo entre dois prèmios");
        assertNotNull(response.getMax(), "O serviço não está retornando o produtor com maior intervalo entre dois prèmios");

        assertEquals(response.getMin().size(), 1,
    "Apenas um produtor poderia ser apontado como ganhador com menor intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(response.getMax().size(), 1,
    "Apenas um produtor poderia ser apontado como ganhador com maior intervalo entre dois prêmios usando a massa de testes de integração");

        ProducerAwardsIntervalResponse producerMin = response.getMin().getFirst();
        ProducerAwardsIntervalResponse producerMax = response.getMax().getFirst();

        // Testes do retorno do ganhador com menor intervalo entre prêmios
        assertEquals("Joel Silver", producerMin.getProducer(),
    "Joel Silver deveria ser apontado como ganhador com menor intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(1990, producerMin.getPreviousWin(),
    "1990 deveria ser apontado como o ano do primeiro prêmio do menor intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(1991, producerMin.getFollowingWin(),
    "1991 deveria ser apontado como o ano do segundo prêmio do menor intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(1, producerMin.getInterval(),
    "1 deveria ser apontado como o menor intervalo entre dois prêmios usando a massa de testes de integração");

        // Testes do retorno do ganhador com maior intervalo entre prêmios
        assertEquals("Matthew Vaughn", producerMax.getProducer(),
    "Matthew Vaughn deveria ser apontado como ganhador com maior intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(2002, producerMax.getPreviousWin(),
    "2002 deveria ser apontado como o ano do primeiro prêmio do maior intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(2015, producerMax.getFollowingWin(),
    "2015 deveria ser apontado como o ano do segundo prêmio do maior intervalo entre dois prêmios usando a massa de testes de integração");
        assertEquals(13, producerMax.getInterval(),
    "13 deveria ser apontado como o maior intervalo entre dois prêmios usando a massa de testes de integração");

    }
}

