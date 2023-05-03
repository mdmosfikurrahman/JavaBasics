package org.epde.basic.IO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class InputOutput {

    public static void main(String[] args) {

        // Reading user input
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Hello, " + name + "!");

        // Writing output to the console
        int x = 10;
        System.out.println("The value of x is: " + x);

        // Reading data from a file
        try {
            File file = new File("ReadFile.txt");
            FileReader reader = new FileReader(file);
            int c;
            System.out.println("Contents of " + file.getName() + ":");
            while ((c = reader.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        // Writing data to a file
        try {
            FileWriter writer = new FileWriter("NewFile.txt");
            writer.write("Hello, world!");
            writer.close();
            System.out.println("Data written to file successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}

