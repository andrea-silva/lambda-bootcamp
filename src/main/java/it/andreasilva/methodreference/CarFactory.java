package it.andreasilva.methodreference;

import it.andreasilva.domain.Car;
import it.andreasilva.domain.Color;

public class CarFactory {
    Car create(String maker, String model, Color color, int speed, TetraFunction<String,String,Color,Integer,Car> function){
        return function.apply(maker, model, color, speed);
    }
}
