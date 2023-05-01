package org.epde.variables;

public class IntegerSwapping {
    public static void main(String[] args) {

        // We have one two prices, need a price swap.

        int firstPrice = 10;
        int secondPrice = 15;

        System.out.println("First Price Is : " + firstPrice);           // 10
        System.out.println("Second Price Is : " + secondPrice);         // 15

        // Need a temporary place to swap

        int tempPrice;

        tempPrice = firstPrice;
        firstPrice = secondPrice;
        secondPrice = tempPrice;

        System.out.println("\nNow,\nFirst Price Is : " + firstPrice);   // 15
        System.out.println("Second Price Is : " + secondPrice);         // 10

        // It is money laundering to take temporary price! So, need swap again

        firstPrice = firstPrice + secondPrice;
        secondPrice = firstPrice - secondPrice;
        firstPrice = firstPrice - secondPrice;

        System.out.println("\nNow,\nFirst Price Is : " + firstPrice);   // 10
        System.out.println("Second Price Is : " + secondPrice);         // 15


    }
}
