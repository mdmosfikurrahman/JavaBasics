package org.epde.oop.inheritence;

// Define the parent class
public class Animal {
    // Define properties
    protected String name;
    protected int age;

    // Define a constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Define a method
    public void makeSound() {
        System.out.println("The animal makes a sound.");
    }
}
