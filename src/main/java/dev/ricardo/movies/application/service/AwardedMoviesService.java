package dev.ricardo.movies.application.service;

import dev.ricardo.movies.domain.AwardYearComparator;
import dev.ricardo.movies.domain.IAwardedProducer;
import dev.ricardo.movies.domain.ProducerAwardsInterval;
import dev.ricardo.movies.domain.TopAwardedProducers;
import dev.ricardo.movies.domain.gateway.MovieGateway;
import dev.ricardo.movies.infrastructure.mapper.MovieMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Component
public class AwardedMoviesService {

    private final MovieMapper mapper;
    private final MovieGateway gateway;

    @Autowired
    public AwardedMoviesService(MovieMapper mapper, MovieGateway gateway) {
        this.mapper = mapper;
        this.gateway = gateway;
    }

    public TopAwardedProducers execute() {
        List<IAwardedProducer> awardedProducers = gateway.retrieveAwardedProducersOrderedByProducerAndYear();

        Map<String, List<IAwardedProducer>> producersMap = awardedProducers.stream()
                .collect(groupingBy(IAwardedProducer::getProducerName));

        List<ProducerAwardsInterval> minIntervals = new ArrayList<>();
        List<ProducerAwardsInterval> maxIntervals = new ArrayList<>();

        for (String producerName: producersMap.keySet()) {
            System.out.println(producerName);
            List<IAwardedProducer> awards = producersMap.get(producerName);
            AwardYearComparator awardYearComparator = new AwardYearComparator();
            awards.sort(awardYearComparator);
            int currentMaxInterval = awards.getLast().getRefYear() - awards.getFirst().getRefYear();
            if (maxIntervals.isEmpty() || currentMaxInterval >= maxIntervals.get(0).getInterval()) {
                if (!maxIntervals.isEmpty() && currentMaxInterval > maxIntervals.get(0).getInterval()) {
                    maxIntervals.clear();
                }
                ProducerAwardsInterval producerAward = ProducerAwardsInterval.builder()
                        .producer(producerName)
                        .previousWin(awards.getFirst().getRefYear())
                        .followingWin(awards.getLast().getRefYear())
                        .interval(currentMaxInterval)
                        .build();
                maxIntervals.add(producerAward);
            }
            int previousRefYear = 0;
            for (IAwardedProducer award: awards) {
                System.out.println("   " + award.getMovieTitle() + " - " + award.getRefYear());
                if (previousRefYear != 0) {
                    if (minIntervals.isEmpty() || award.getRefYear() - previousRefYear <= minIntervals.get(0).getInterval()) {
                        if (!minIntervals.isEmpty() && award.getRefYear() - previousRefYear < maxIntervals.get(0).getInterval()) {
                            minIntervals.clear();
                        }
                        ProducerAwardsInterval minInterval = ProducerAwardsInterval.builder()
                                .producer(producerName)
                                .previousWin(previousRefYear)
                                .followingWin(award.getRefYear())
                                .interval(award.getRefYear() - previousRefYear)
                                .build();
                        minIntervals.add(minInterval);
                    }
                }
                previousRefYear = award.getRefYear();
            }
        }
        return TopAwardedProducers.builder()
                .min(minIntervals)
                .max(maxIntervals)
                .build();
    }

}
