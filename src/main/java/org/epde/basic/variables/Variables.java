package org.epde.basic.variables;

import java.util.Arrays;

public class Variables {

    public static void main(String[] args) {

        // declaring and initializing variables of different data types
        int myInt = 42;
        double myDouble = 3.14159;
        boolean myBoolean = true;
        char myChar = 'A';
        String myString = "Hello, world!";
        int[] myIntArray = {1, 2, 3, 4, 5};

        // outputting the values of the variables
        System.out.println("myInt = " + myInt);
        System.out.println("myDouble = " + myDouble);
        System.out.println("myBoolean = " + myBoolean);
        System.out.println("myChar = " + myChar);
        System.out.println("myString = " + myString);
        System.out.println("myIntArray = " + Arrays.toString(myIntArray));

        // performing type casting
        int myNewInt = (int) myDouble;
        System.out.println("myNewInt = " + myNewInt);

        // using constants
        final double PI = 3.14159;
        double circleRadius = 5.0;
        double circleArea = PI * (circleRadius * circleRadius);
        System.out.println("circleArea = " + circleArea);
    }
}
