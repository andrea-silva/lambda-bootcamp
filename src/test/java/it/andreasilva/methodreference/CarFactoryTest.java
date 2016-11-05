package it.andreasilva.methodreference;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.Color;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CarFactoryTest {
    private CarFactory factory = new CarFactory();
    @Test
    public void standardCarIsBuilt() throws Exception {
        Car car = factory.create("Tesla", "Roadster", Color.BLUE, 300, Car::new);

        assertThat(car.getMaker(), is(equalTo("Tesla")));
        assertThat(car.getModel(), is(equalTo("Roadster")));
        assertThat(car.getColor(), is(equalTo(Color.BLUE)));
        assertThat(car.getSpeed(), is(equalTo(300)));
    }

    @Test
    public void pimpedCarIsBuilt() throws Exception {
        Car car = factory.create("Fiat", "Panda", Color.BLUE, 100, (String maker, String model, Color color, Integer speed) -> new Car(maker, "Pimped" +model, Color.GREEN, speed * 3));

        assertThat(car.getMaker(), is(equalTo("Fiat")));
        assertThat(car.getModel(), is(equalTo("PimpedPanda")));
        assertThat(car.getColor(), is(equalTo(Color.GREEN)));
        assertThat(car.getSpeed(), is(equalTo(300)));
    }
}