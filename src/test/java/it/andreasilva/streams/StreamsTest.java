package it.andreasilva.streams;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.CarCatalog;
import it.andreasilva.domain.Color;
import it.andreasilva.domain.java8inaction.Trader;
import it.andreasilva.domain.java8inaction.Transaction;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.minBy;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testMatchMethods() throws Exception {
        final List<Car> cars = CarCatalog.getCars();

        assertTrue(cars.stream().allMatch(car -> car.getSpeed() > 80));
        assertTrue(cars.stream().anyMatch(car -> car.getSpeed() > 250));
        assertTrue(cars.stream().noneMatch(car -> car.getSpeed() > 450));
    }

    @Test
    public void testFindMethods() throws Exception {
        final Optional<Car> match = CarCatalog.getCars().stream().filter(car -> car.getColor() == Color.BLACK).findAny();

        assertTrue(match.isPresent());
    }

    @Test
    public void testReducingWithoutOptional() throws Exception {
        final Car defaultCar = new Car("TOYOTA", "Yaris", Color.GREY, 165);
        final BinaryOperator<Car> carBinaryOperator = (car1, car2) -> car1.getSpeed() > car2.getSpeed() ? car1 : car2;
        final Car car = CarCatalog.getCars().stream().reduce(defaultCar, carBinaryOperator);

        assertThat(car, hasProperty("model", equalTo("Testarossa")));
        assertThat(Stream.<Car>of().reduce(defaultCar, carBinaryOperator), hasProperty("model", equalTo("Yaris")));
    }

    @Test
    public void canFindTransactionsForYear() throws Exception {
        final List<Transaction> transactions = Transaction.transactions.stream()
                .filter(t -> t.getYear() == 2011)
                .sorted(comparing(Transaction::getValue)).collect(toList());

        assertThat(transactions, containsInRelativeOrder(
                hasProperty("value", equalTo(300)),
                hasProperty("value", equalTo(400))));
    }

    @Test
    public void canFindTradersCities() throws Exception {
        final List<String> cities = Transaction.transactions.stream()
                .map(t -> t.getTrader().getCity())
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        assertThat(cities, containsInRelativeOrder("Cambridge", "Milan"));
    }

    @Test
    public void canFindTradersForCity() throws Exception {
        final List<Trader> traders = Transaction.transactions.stream()
                .map(t -> t.getTrader())
                .distinct()
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .sorted(comparing(Trader::getName))
                .collect(toList());

        assertThat(traders.size(), equalTo(3));
        assertThat(traders, containsInRelativeOrder(
                hasProperty("name", equalTo("Alan")),
                hasProperty("name", equalTo("Brian")),
                hasProperty("name", equalTo("Raoul"))
                ));
    }

    @Test
    public void canFindAllTradersAsString() throws Exception {
        final String traders = Transaction.transactions.stream()
                .map(t -> t.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", String::concat);

        assertEquals("AlanBrianMarioRaoul", traders);
    }

    @Test
    public void canCheckIfThereIsATraderInTown() throws Exception {
        final boolean tradersInMilan = Transaction.transactions.stream().anyMatch(t -> t.getTrader().getCity().equals("Milan"));

        assertTrue(tradersInMilan);
    }

    @Test
    public void canFindTransactionWithHighestValue() throws Exception {
        final Integer max = Transaction.transactions.stream().mapToInt(t -> t.getValue()).reduce(0, Integer::max);

        assertThat(max, equalTo(1000));
    }

    @Test
    public void canFindTransactionWithSmallestValue() throws Exception {
        final Optional<Transaction> minTransaction = Transaction.transactions.stream().
                collect(minBy(comparing(Transaction::getValue)));

        assertThat(minTransaction.get().getValue(), equalTo(300));
    }

    @Test
    public void canProducePythagoreanTriples() throws Exception {
        final List<double[]> pythagoreanTriples = IntStream.rangeClosed(1, 100)
                .boxed()
                .flatMap(a -> IntStream.rangeClosed(a, 100).mapToObj(b -> new double[]{a, b, Math.sqrt(a * a + b * b)}))
                .filter(t -> t[2] % 1 == 0)
                .collect(toList());

        assertThat(pythagoreanTriples, containsInRelativeOrder(
                new double[]{3, 4, 5},
                new double[]{7, 24, 25}
                ));
    }

    @Test
    public void canCountNumberOfUniqueWordsInFile() throws Exception {
        try(Stream<String> lines = Files.lines(Paths.get(this.getClass().getClassLoader().getResource("words.txt").getFile().substring(1)))) {
            final long count = lines.flatMap(line -> Arrays.stream(line.split(" ")))
                    .distinct()
                    .count();

            assertThat(count, equalTo(5L));
        }
    }

    @Test
    public void canProduceFibonacci() throws Exception {
        final int[] fibonacci = Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .mapToInt(t -> t[0])
                .limit(5)
                .toArray();

        assertThat(fibonacci, equalTo(new int[]{0, 1, 1, 2, 3}));
    }
}
