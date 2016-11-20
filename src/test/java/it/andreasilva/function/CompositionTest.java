package it.andreasilva.function;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.function.BiFunction;
import java.util.function.Function;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CompositionTest {
    @Test
    public void functionsCanBeChainedOrComposed() throws Exception {
        final BiFunction<Integer,Integer,Integer> product = (x, y) -> x * y;
        final Function<Integer,Integer> square = (x) -> x * x;
        final Function<Integer,Integer> timesTwo = (x) -> 2 * x;
        final BiFunction<Integer, Integer, Integer> productAndThenSquare = product.andThen(square);


        assertThat(productAndThenSquare.apply(5,2), Matchers.is(equalTo(100)));
        assertThat(square.compose(timesTwo).apply(3), Matchers.is(equalTo(36)));
    }
}
