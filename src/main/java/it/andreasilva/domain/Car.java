package it.andreasilva.domain;

public class Car {
    private final String maker;
    private final String model;
    private final Color color;
    private final int speed;

    public Car(String maker, String model, Color color, int speed) {
        this.maker = maker;
        this.model = model;
        this.color = color;
        this.speed = speed;
    }

    public String getMaker() {
        return maker;
    }

    public String getModel() {
        return model;
    }

    public Color getColor() {
        return color;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "maker='" + maker + '\'' +
                ", model='" + model + '\'' +
                ", color=" + color +
                ", speed=" + speed +
                '}';
    }
}
