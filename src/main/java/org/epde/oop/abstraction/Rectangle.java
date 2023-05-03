package org.epde.oop.abstraction;

// Example of a concrete subclass that extends the abstract class
public class Rectangle extends Shape {
    private final double length;
    private final double width;

    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Implementation of the abstract method from the Shape class
    public double getArea() {
        return length * width;
    }
}
