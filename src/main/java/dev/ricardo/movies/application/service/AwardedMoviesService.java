package dev.ricardo.movies.application.service;

import dev.ricardo.movies.domain.AwardYearComparator;
import dev.ricardo.movies.domain.IAwardedProducer;
import dev.ricardo.movies.domain.ProducerAwardsInterval;
import dev.ricardo.movies.domain.TopAwardedProducers;
import dev.ricardo.movies.domain.gateway.MovieGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static java.util.stream.Collectors.groupingBy;

@Component
public class AwardedMoviesService {

    private static final Logger log = LoggerFactory.getLogger(AwardedMoviesService.class);

    private final MovieGateway gateway;

    @Autowired
    public AwardedMoviesService(MovieGateway gateway) {
        this.gateway = gateway;
    }

    public TopAwardedProducers execute() {
        List<IAwardedProducer> awardedProducers = gateway.retrieveAwardedProducersOrderedByProducerAndYear();

        Map<String, List<IAwardedProducer>> producersMap = awardedProducers.stream()
                .collect(groupingBy(IAwardedProducer::getProducerName));

        List<ProducerAwardsInterval> minIntervals = new ArrayList<>();
        List<ProducerAwardsInterval> maxIntervals = new ArrayList<>();

        int minInterval = Integer.MAX_VALUE;
        int maxInterval = Integer.MIN_VALUE;
        for (String producerName: producersMap.keySet()) {
            log.info("Processing producer {}", producerName);
            List<IAwardedProducer> awards = producersMap.get(producerName);
            AwardYearComparator awardYearComparator = new AwardYearComparator();
            awards.sort(awardYearComparator);
            Integer previousAwardYear = null;
            for (IAwardedProducer award: awards) {
                log.info("Producer awarded: {} - Movie: {} - Year: {}", producerName, award.getMovieTitle(), award.getRefYear());
                if (Objects.isNull(previousAwardYear)) {
                    previousAwardYear = award.getRefYear();
                    continue;
                }
                int yearsSincePreviousAward = award.getRefYear() - previousAwardYear;
                // Verifying if this interval is less than the minimum interval found

                if (yearsSincePreviousAward <= minInterval) {
                    if (yearsSincePreviousAward < minInterval) {
                        minInterval = yearsSincePreviousAward;
                        minIntervals.clear();
                    }
                    ProducerAwardsInterval awardsInterval = ProducerAwardsInterval.builder()
                            .producer(producerName)
                            .previousWin(previousAwardYear)
                            .followingWin(award.getRefYear())
                            .interval(yearsSincePreviousAward)
                            .build();
                    minIntervals.add(awardsInterval);
                }
                // Verifying if this interval is greater than the maximum interval found
                if (yearsSincePreviousAward >= maxInterval) {
                    if (yearsSincePreviousAward > maxInterval) {
                        maxInterval = yearsSincePreviousAward;
                        maxIntervals.clear();
                    }
                    ProducerAwardsInterval awardsInterval = ProducerAwardsInterval.builder()
                            .producer(producerName)
                            .previousWin(previousAwardYear)
                            .followingWin(award.getRefYear())
                            .interval(yearsSincePreviousAward)
                            .build();
                    maxIntervals.add(awardsInterval);
                }
                previousAwardYear = award.getRefYear();
            }
        }
        return TopAwardedProducers.builder()
                .min(minIntervals)
                .max(maxIntervals)
                .build();
    }

}
