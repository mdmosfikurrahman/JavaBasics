package org.epde.oop.abstraction;

// Example of a program that uses the Shape and Rectangle classes
public class Main {

    public static void main(String[] args) {
        Rectangle rectangle = new Rectangle(5, 10);
        rectangle.printArea(); // Output: The area is: 50.0

        Triangle triangle = new Triangle(3, 4);
        System.out.println("The Base is:"+ triangle.getBase());
        System.out.println("The Height is:"+ triangle.getHeight());
        System.out.println("The Area is:"+ triangle.getArea());
    }
}