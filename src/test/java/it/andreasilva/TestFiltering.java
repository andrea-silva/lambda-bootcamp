package it.andreasilva;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.CarCatalog;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@SuppressWarnings("unchecked")
public class TestFiltering {

    @Test
    public void canFilterByMaker() throws Exception {
        List<Car> ferrari = CarCatalog.getCars(car -> "FERRARI".equalsIgnoreCase(car.getMaker()));
        assertThat(ferrari, hasSize(1));
        assertThat(ferrari, hasItem(allOf(
                hasProperty("model", equalTo("Testarossa")),
                hasProperty("speed", equalTo(275)))));
    }

    @Test
    public void canFilterBySpeed() throws Exception {
        List<Car> cars = CarCatalog.getCars(car -> car.getSpeed() > 150);
        assertThat(cars, hasSize(5));
        assertThat(cars, hasItem(hasProperty("model", equalTo("Testarossa"))));
        assertThat(cars, hasItem(hasProperty("model", equalTo("Class C"))));
        assertThat(cars, hasItem(hasProperty("model", equalTo("Giulietta"))));
        assertThat(cars, hasItem(hasProperty("model", equalTo("Mini"))));
        assertThat(cars, hasItem(hasProperty("model", equalTo("Mustang"))));
    }

    @Test
    public void canSortBySpeed() throws Exception {
        final List<Car> cars = CarCatalog.getCars();
        cars.sort((car1, car2) -> car1.getSpeed() - car2.getSpeed());
        assertThat(cars, containsInRelativeOrder(
                hasProperty("model", equalTo("126")),
                hasProperty("model", equalTo("Panda")),
                hasProperty("model", equalTo("Kia"))
        ));
    }
}
