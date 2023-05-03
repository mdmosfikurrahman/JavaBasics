package org.epde.basic.array;

import java.util.ArrayList;
import java.util.Arrays;

public class Array {

    public static void main(String[] args) {

        // Declaring and initializing arrays
        int[] numbers = {1, 2, 3, 4, 5};
        String[] names = new String[3];
        names[0] = "Mosfik";
        names[1] = "Farzana";
        names[2] = "Empress";

        // Accessing array elements
        System.out.println(numbers[2]);
        System.out.println(names[1]);

        // Array operations
        System.out.println(numbers.length);
        int[] copy = Arrays.copyOf(numbers, 3);
        System.out.println(Arrays.toString(copy));
        Arrays.sort(numbers);
        System.out.println(Arrays.toString(numbers));
        System.out.println(Arrays.binarySearch(numbers, 4));

        // Multidimensional arrays
        int[][] matrix = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};
        System.out.println(matrix[1][2]);

        // ArrayList
        ArrayList<String> colors = new ArrayList<>();
        colors.add("red");
        colors.add("green");
        colors.add("blue");
        System.out.println(colors.get(1));

        // Enhanced for loop
        for (int number : numbers) {
            System.out.print(number + " ");
        }
        System.out.println();

        // Arrays of objects
        Person[] people = new Person[3];
        people[0] = new Person("Mosfik", 30);
        people[1] = new Person("Farzana", 25);
        people[2] = new Person("Empress", 40);
        Arrays.sort(people);
        for (Person person : people) {
            System.out.println(person.getName() + " " + person.getAge());
        }

        // Arrays in algorithms
        int[] array = {5, 3, 1, 4, 2};
        Arrays.sort(array);
        System.out.println(Arrays.toString(array));
    }
}

