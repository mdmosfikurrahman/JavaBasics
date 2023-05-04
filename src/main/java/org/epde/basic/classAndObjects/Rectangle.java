package org.epde.basic.classAndObjects;

public class Rectangle implements Shape {
    private final double length;
    private final double width;

    // Constructor for Rectangle class
    public Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Implement the getArea() method for Rectangle
    public double getArea() {
        return length * width;
    }

    // Implement the getPerimeter() method for Rectangle
    public double getPerimeter() {
        return 2 * (length + width);
    }
}

