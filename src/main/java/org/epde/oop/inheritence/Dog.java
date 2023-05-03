package org.epde.oop.inheritence;

// Define another child class that inherits from the parent class
public class Dog extends Animal{

    // Define a constructor that calls the parent constructor
    public Dog(String name, int age) {
        super(name, age);
    }

    // Override the makeSound() method with specialized behavior
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }

}
