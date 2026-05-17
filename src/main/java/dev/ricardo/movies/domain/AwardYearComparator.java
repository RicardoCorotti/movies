package dev.ricardo.movies.domain;

import java.util.Comparator;

public class AwardYearComparator implements Comparator<IAwardedProducer> {

    @Override
    public int compare(IAwardedProducer o1, IAwardedProducer o2) {
        return o1.getRefYear().compareTo(o2.getRefYear());
    }

}
