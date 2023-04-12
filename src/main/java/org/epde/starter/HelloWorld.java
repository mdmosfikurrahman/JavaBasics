package org.epde.starter;

public class HelloWorld {
    public static void main(String[] args) {
        // Let's Begin
        System.out.println("Hello world!");

        /*
        Difference between println and print
        "println" is used for a new line to print
        "print" is used for print wherever it is
         */
        System.out.print("Hello world!\n");

        // The following commented line will cause error if we want to view output as - "Hello world!"
        // System.out.println(""Hello world!"");

        // To do this
        System.out.println("\"Hello world!\"");

        // for new line just as C programming, you can use \n
        System.out.println("Hey,\n\nHello world!");

        // for new tab just as C programming, you can use \t
        System.out.println("Hey,\t\tHello world!");
    }
}