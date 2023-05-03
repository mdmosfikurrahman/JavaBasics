package org.epde.basic.exceptionHandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExceptionHandling {

    public static void main(String[] args) {

        // Example 1: Try-catch block
        try {
            int result = 10 / 0; // This will throw an ArithmeticException
            System.out.println(result);
        } catch (ArithmeticException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // Example 2: Multiple catch blocks
        try {
            int[] arr = new int[5];
            arr[10] = 5; // This will throw an ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index out of bounds: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // Example 3: Finally block
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File("NonExistentFile.txt")); // This will throw a FileNotFoundException
            System.out.println(scanner.nextLine());
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }

        // Example 4: Throwing an exception
        try {
            int age = -5;
            if (age < 0) {
                throw new IllegalArgumentException("Age cannot be negative");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

