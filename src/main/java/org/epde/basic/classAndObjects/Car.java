package org.epde.basic.classAndObjects;

public class Car {
    // Instance variables
    private final String make;
    private final String model;
    private final String color;
    private boolean engineRunning;

    // Constructor
    public Car(String make, String model, String color) {
        this.make = make;
        this.model = model;
        this.color = color;
        this.engineRunning = false;
    }

    // Instance methods
    public void startEngine() {
        this.engineRunning = true;
        System.out.println("Engine started.");
    }

    public void stopEngine() {
        this.engineRunning = false;
        System.out.println("Engine stopped.");
    }

    public String getMake() {
        return this.make;
    }

    public String getModel() {
        return this.model;
    }

    public String getColor() {
        return this.color;
    }

    // Static method
    public static void honkHorn() {
        System.out.println("Honk honk!");
    }
}