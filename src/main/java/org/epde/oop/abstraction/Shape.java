package org.epde.oop.abstraction;

// Example of an abstract class in Java
public abstract class Shape {
    // An abstract method has no implementation and must be implemented in the subclass
    public abstract double getArea();

    // A concrete method has a default implementation that can be inherited by subclasses
    public void printArea() {
        System.out.println("The area is: " + getArea());
    }
}
