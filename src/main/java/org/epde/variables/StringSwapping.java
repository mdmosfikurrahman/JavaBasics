package org.epde.variables;

public class StringSwapping {
    public static void main(String[] args) {

        // We have one bowl of rice and one bowl of lentils, need a bowl swap.

        String firstBowl = "Rice";
        String secondBowl = "Lentils";

        System.out.println("First Bowl Have : " + firstBowl);           // Rice
        System.out.println("Second Bowl Have : " + secondBowl);         // Lentils

        // Need a blank bowl to swap

        String blankBowl;

        blankBowl = firstBowl;
        firstBowl = secondBowl;
        secondBowl = blankBowl;

        System.out.println("\nNow,\nFirst Bowl Have : " + firstBowl);   // Lentils
        System.out.println("Second Bowl Have : " + secondBowl);         // Rice

        // Now, blank Bowl has been stolen, and we don't have any extra bowl, need swap again.

        firstBowl = firstBowl + secondBowl;
        secondBowl = firstBowl.substring(0, firstBowl.length() - secondBowl.length());
        firstBowl = firstBowl.substring(secondBowl.length());

        System.out.println("\nNow,\nFirst Bowl Have : " + firstBowl);   // Rice
        System.out.println("Second Bowl Have : " + secondBowl);         // Lentils
    }
}
