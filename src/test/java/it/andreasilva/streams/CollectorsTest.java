package it.andreasilva.streams;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.CarCatalog;
import it.andreasilva.domain.Color;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertThat;

public class CollectorsTest {

    @Test
    public void canSummarizeIntProperty() throws Exception {
        final IntSummaryStatistics statistics = CarCatalog.getCars().stream().mapToInt(Car::getSpeed).summaryStatistics();

        assertThat(statistics.getCount(), equalTo(11L));
        assertThat(statistics.getMax(), equalTo(275));
        assertThat(statistics.getMin(), equalTo(125));
        assertThat(statistics.getAverage(), Matchers.closeTo(174.3D, 0.5D));
    }

    @Test
    public void canJoin() throws Exception {
        final String modelsString = CarCatalog.getCars().stream().map(Car::getModel).distinct().sorted(String::compareToIgnoreCase).collect(joining(", "));
        assertThat(modelsString, equalTo("126, Class C, Giulietta, Kia, Mini, Multipla, Mustang, Panda, Testarossa, Yaris"));
    }

    private static <T>  Collector<? super T, ?, Long> counting() {
        return reducing(0L, e -> 1L, (a, b) -> a+b);
    }

    @Test
    public void canCount() throws Exception {
        final Long countWithMyCounter = CarCatalog.getCars().stream().collect(counting());
        final Long count = CarCatalog.getCars().stream().collect(Collectors.counting());

        assertThat(countWithMyCounter, equalTo(count));
    }

    @Test
    public void groupByColorAnModel() {
        final Map<Color, Map<String, List<Car>>> grouped = CarCatalog.getCars().stream().collect(groupingBy(Car::getColor, groupingBy(Car::getMaker)));

        assertThat(grouped.get(Color.BLACK).get("FIAT"), contains(hasProperty("model", equalTo("Panda"))));
    }

    @Test
    public void maxSpeedByMaker() {
        final Map<String, Integer> grouped = CarCatalog.getCars().stream()
                .collect(groupingBy(Car::getMaker, collectingAndThen(maxBy(comparingInt(Car::getSpeed)), e -> e.get().getSpeed())));

        assertThat(grouped.get("FIAT"), equalTo(150));
    }
}
