package org.epde.oop.inheritence;

// Define a child class that inherits from the parent class
public class Cat extends Animal{

    // Define a constructor that calls the parent constructor
    public Cat(String name, int age) {
        super(name, age);
    }

    // Override the makeSound() method with specialized behavior
    @Override
    public void makeSound() {
        System.out.println("Meow!");
    }
}
