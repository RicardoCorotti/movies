package dev.ricardo.movies;

import dev.ricardo.movies.domain.Producer;
import dev.ricardo.movies.infrastructure.api.dto.TopAwardedProducersResponse;
import dev.ricardo.movies.infrastructure.mapper.MovieMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoviesApplicationTests {

    @LocalServerPort
    private int port;

    private String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;

    @Autowired
    private TestProducerRepository testProducerRepository;

    @Autowired
    private MovieMapper mapper;

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setup() {
        baseUrl = baseUrl.concat(":").concat(port+"").concat("/awarded-movies");
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testAwardedMovies() {
        Producer producer = Producer.newProducer("Producer 1");
        testProducerRepository.save(mapper.domainToEntity(producer));

        TopAwardedProducersResponse response = restTemplate.getForEntity(baseUrl, TopAwardedProducersResponse.class).getBody();

        assertNotNull(response);

    }

}
