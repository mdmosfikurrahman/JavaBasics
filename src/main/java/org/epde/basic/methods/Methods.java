package org.epde.basic.methods;

public class Methods {

    // Defining a method
    public static void greet() {
        System.out.println("Hello, world!");
    }

    // Defining a method with parameters
    public static void greetPerson(String name) {
        System.out.println("Hello, " + name + "!");
    }

    // Defining an overloaded method with different parameters
    public static void greetPerson(String firstName, String lastName) {
        System.out.println("Hello, " + firstName + " " + lastName + "!");
    }

    // Defining a method with a return type
    public static int add(int x, int y) {
        return x + y;
    }

    // Defining a method with a void return type and access modifier
    private static void printMessage(String message) {
        System.out.println(message);
    }

    // Defining a recursive method
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return n * factorial(n - 1);
        }
    }

    public static void main(String[] args) {

        // Calling methods
        greet();
        greetPerson("Mosfik");
        greetPerson("Farzana", "Yesmin");
        int sum = add(3, 4);
        System.out.println("3 + 4 = " + sum);
        printMessage("This is a private message.");
        int result = factorial(5);
        System.out.println("Factorial of 5 is " + result);
    }
}

