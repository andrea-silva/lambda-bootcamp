package it.andreasilva.compare;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.CarCatalog;
import org.junit.Test;

import java.util.Comparator;
import java.util.List;

import static it.andreasilva.compare.CompareUtils.myComparing;
import static java.util.Comparator.comparing;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class CompareUtilsTest {
    @Test
    public void myComparingBehavesAsComparatorComparing() throws Exception {
        final List<Car> myCars = CarCatalog.getCars();
        final List<Car> cars = CarCatalog.getCars();
        myCars.sort(myComparing(Car::getMaker));
        cars.sort(comparing(Car::getMaker));

        assertThat(myCars, is(equalTo(cars)));
    }

    @Test
    public void composingComparators() throws Exception {
        final List<Car> cars = CarCatalog.getCars();
        final Comparator<Car> makerComparator = comparing(Car::getMaker).reversed();
        final Comparator<Car> modelComparator = comparing(Car::getModel).reversed();
        cars.sort(makerComparator.thenComparing(modelComparator));

        assertThat(cars.get(0).getMaker(), is(equalTo("TOYOTA")));
        assertThat(cars.get(1).getMaker(), is(equalTo("MERCEDES")));
        assertThat(cars.get(2).getMaker(), is(equalTo("FORD")));
        assertThat(cars.get(2).getModel(), is(equalTo("Mustang")));
        assertThat(cars.get(3).getMaker(), is(equalTo("FORD")));
        assertThat(cars.get(3).getModel(), is(equalTo("Kia")));
    }
}