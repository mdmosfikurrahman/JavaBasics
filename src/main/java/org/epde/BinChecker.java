package org.epde;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class BinChecker {

    public static void main(String[] args) throws IOException {
        InputStream inputStream = BinChecker.class.getResourceAsStream("/input.csv");

        if (inputStream == null) {
            System.err.println("Input file not found");
            return;
        }

        Scanner scanner = new Scanner(inputStream);

        FileWriter writer = new FileWriter("output.csv");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(",");

            if (tokens.length > 0) {
                String binNumber = tokens[0].trim();

                if (isValidBinNumber(binNumber)) {
                    writer.write(binNumber + "\n");
                }
            }
        }

        scanner.close();
        writer.close();
    }

    private static boolean isValidBinNumber(String binNumber) {
        if (binNumber.length() != 14) {
            return false;
        }

        return binNumber.charAt(0) == '0' && binNumber.charAt(10) == '0';
    }
}
