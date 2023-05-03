package org.epde.oop.polymorphism;

public class Calculation {

    // Method overloading: compile-time polymorphism
    public int add(int x, int y) {
        return x + y;
    }

    public int add(int x, int y, int z) {
        return x + y + z;
    }

    public double add(double x, double y) {
        return x + y;
    }
}
