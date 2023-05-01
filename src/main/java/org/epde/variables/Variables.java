package org.epde.variables;

public class Variables {
    public static void main(String[] args) {

        /*
        Let's create a variable first with the following steps.
        Step 1: Declare
        Step 2: Assignment
        Step 3: Initialization (Step 1 + Step 2)
         */

        String myName;      // Step 1
        myName = "Mosfik";  // Step 2

        /*
        So, here variable named "myName" is declared and assigned with a value.
        Let's show the output here.
         */

        System.out.println(myName);

        /*
        Let's combine first two steps and make the third step real to show output.
         */
        int myInt = 10;     // Step 3
        System.out.println(myInt);

        /*
        There are two types of variables.
        1. Primitive:
        Specs:
            a. 8 Types only (boolean, byte, short etc...)
            b. Stores data
            c. Can only hold 1 value
            d. Less Memory
            e. Fast
        Examples: Boolean, Byte, Short, Long, Int, Float, Double, Char
        */

        int anInt = 10;
        double aDouble = 10.50;
        boolean aBoolean = true;
        char aChar = '@';
        byte aByte = 1;
        short aShort = 15;
        long aLong = 2345564L;

        System.out.println("This is an Integer Value: " + anInt);
        System.out.println("This is a Double Value: " + aDouble);
        System.out.println("This is a Boolean Value: " + aBoolean);
        System.out.println("This is a Symbol Character: " + aChar);
        System.out.println("This is a Byte Value: " + aByte);
        System.out.println("This is a Short Value: " + aShort);
        System.out.println("This is a Long Value: " + aLong);

        /*
        2. Reference/Non-primitive:
        Specs:
            a. Unlimited (User-Defined)
            b. Stores address
            c. Can only hold more than 1 value
            d. More Memory
            e. Slow
        Example: String
         */

        String aString = "Hello World";

        System.out.println("This is a String: " + aString);
    }
}
