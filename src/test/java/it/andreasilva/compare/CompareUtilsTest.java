package it.andreasilva.compare;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.CarCatalog;
import org.junit.Test;

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
}