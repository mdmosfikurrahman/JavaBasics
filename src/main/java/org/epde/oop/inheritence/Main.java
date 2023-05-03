package org.epde.oop.inheritence;

// Create some animal objects and call their methods
public class Main {

    public static void main(String[] args) {
        Animal animal = new Animal("Generic Animal", 5);
        Cat cat = new Cat("Fluffy", 3);
        Dog dog = new Dog("Rufus", 7);

        animal.makeSound();
        cat.makeSound();
        dog.makeSound();
    }
}
