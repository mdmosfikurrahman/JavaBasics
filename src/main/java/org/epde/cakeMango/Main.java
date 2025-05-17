package org.epde.cakeMango;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EvenOddChecker checker = new EvenOddChecker();

        try {
            while (true) {
                System.out.println("Enter a number (or type 'exit' to quit):");
                if (scanner.hasNextInt()) {
                    int number = scanner.nextInt();
                    checker.processNumber(number);
                } else if (scanner.hasNext("exit")) {
                    System.out.println("Exiting...");
                    break;
                } else {
                    System.out.println("Invalid input, please enter a number or type 'exit' to quit.");
                    scanner.next();
                }
            }
        } catch (CustomInvalidException e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }

    }
}
