package it.andreasilva.domain;

import java.util.ArrayList;
import java.util.List;

public class CarCatalog {
    private static List<Car> cars = new ArrayList<Car>();

    static {
        cars.add(new Car("FIAT", "Panda", Color.BLACK, 130));
        cars.add(new Car("FIAT", "Panda", Color.RED, 135));
        cars.add(new Car("FIAT", "126", Color.WHITE, 125));
        cars.add(new Car("FIAT", "Multipla", Color.RED, 150));
        cars.add(new Car("FORD", "Mustang", Color.BLACK, 210));
        cars.add(new Car("FORD", "Kia", Color.BLUE, 140));
        cars.add(new Car("TOYOTA", "Yaris", Color.GREEN, 145));
        cars.add(new Car("COOPER", "Mini", Color.GREY, 175));
        cars.add(new Car("FERRARI", "Testarossa", Color.RED, 275));
        cars.add(new Car("MERCEDES", "Class C", Color.GREY, 235));
        cars.add(new Car("ALFAROMEO", "Giulietta", Color.YELLOW, 197));
    }

    public static List<Car> getCars() {
        return cars;
    }
}
