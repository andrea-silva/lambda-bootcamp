package it.andreasilva.streams;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.CarCatalog;
import it.andreasilva.domain.Color;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class StreamsTest {

    @Test
    public void groupByColor() throws Exception {
        final List<Car> cars = CarCatalog.getCars();

        final Map<Color, List<Car>> byColor = cars.stream().collect(Collectors.groupingBy(Car::getColor));

        assertThat(byColor.get(Color.RED).size(), is(equalTo(3)));
    }

    @Test
    public void retreiveTheTwoFastestFiat() throws Exception {
        final List<Car> fiats = CarCatalog.getCars().stream()
                .filter(car -> car.getMaker().equals("FIAT"))
                .sorted(comparing(Car::getSpeed).reversed())
                .limit(2)
                .collect(toList());

        assertThat(fiats.size(), is(equalTo(2)));
        assertThat(fiats, containsInRelativeOrder(
                hasProperty("model", equalTo("Multipla")),
                hasProperty("model", equalTo("Panda"))));
    }

    @Test
    public void distinctLettersInAnArrayOfStrings() throws Exception {
        final String[] strings = {"uno", "due", "tre"};
        final Stream<String> stream = Arrays.stream(strings);

        final List<String> result = stream.flatMap(s -> Arrays.stream(s.split("")))
                .distinct()
                .collect(toList());

        assertThat(result, containsInAnyOrder("u", "n", "o", "d", "e", "t", "r"));
    }

    @Test
    public void squareIntArray() throws Exception {
        int[] numbers = new int[]{1, 2, 3, 4, 5};
        final List<Integer> result = IntStream.of(numbers)
                .map(i -> i * i)
                .boxed()
                .collect(toList());

        assertThat(result, containsInRelativeOrder(1, 4, 9, 16, 25));
    }

    @Test
    public void createPairs() throws Exception {
        String[] array1 = {"a", "b", "c"};
        String[] array2 = {"1", "2", "3"};

        final List<String> result = Arrays.stream(array1)
                .flatMap(s -> Arrays.stream(array2).map(t -> s.concat(t)))
                .collect(toList());

        assertThat(result, containsInRelativeOrder("a1", "a2", "a3", "b1", "b2", "b3", "c1", "c2", "c3"));
    }


}
