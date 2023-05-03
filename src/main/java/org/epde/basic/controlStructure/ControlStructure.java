package org.epde.basic.controlStructure;

import java.util.Scanner;

public class ControlStructure {

    public static void main(String[] args) {

        // Conditional statements example

        int number = 5;

        if (number > 0) {
            System.out.println("The number is positive.");
        } else if (number < 0) {
            System.out.println("The number is negative.");
        } else {
            System.out.println("The number is zero.");
        }

        String dayOfWeek = "Monday";

        switch (dayOfWeek) {
            case "Monday":
                System.out.println("Today is Monday.");
                break;
            case "Tuesday":
                System.out.println("Today is Tuesday.");
                break;
            default:
                System.out.println("Today is not Monday or Tuesday.");
        }

        // Looping statements example

        System.out.println("\nFor Loop:");

        for (int i = 1; i <= 5; i++) {
            System.out.println("Counting: " + i);
        }

        System.out.println("\nWhile Loop:");

        int j = 1;

        while (j <= 5) {
            System.out.println("Counting: " + j);
            j++;
        }

        System.out.println("\nDo While Loop:");

        int k = 1;

        do {
            System.out.println("Counting: " + k);
            k++;
        } while (k <= 5);

        // Branching statements example

        int[] numbers = {1, 2, 3, 4, 5};

        System.out.println("\nBranching Example (break):");

        for (int digit : numbers) {
            if (digit == 3) {
                break;
            }
            System.out.println("Number: " + number);
        }

        System.out.println("\nBranching Example (continue):");

        for (int digit : numbers) {
            if (digit == 3) {
                continue;
            }
            System.out.println("Number: " + number);
        }

        // User input example

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nScanner Example:");

        System.out.println("Enter a number:");
        int inputNumber = scanner.nextInt();

        if (inputNumber % 2 == 0) {
            System.out.println("The number is even.");
        } else {
            System.out.println("The number is odd.");
        }
    }
}