package org.epde.basic.classAndObjects;

public class Main {
    public static void main(String[] args) {
        // Create a new Car object
        Car myCar = new Car("Honda", "Civic", Color.BLUE);

        // Call the startEngine() method
        myCar.startEngine();

        // Call the getMake(), getModel(), and getColor() methods and print the results
        System.out.println("Make: " + myCar.getMake());
        System.out.println("Model: " + myCar.getModel());
        System.out.println("Color: " + myCar.getColor());

        // Call the honkHorn() static method
        Car.honkHorn();

        // Call the stopEngine() method
        myCar.stopEngine();

    Rectangle rect = new Rectangle(5, 10);
        double area = rect.getArea();
        double perimeter = rect.getPerimeter();
        System.out.println("Area: " + area);
        System.out.println("Perimeter: " + perimeter);
    }
}
