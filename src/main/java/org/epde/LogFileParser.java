package org.epde;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogFileParser {
    public static void main(String[] args) {
        String filePath = "src/main/resources/application.log";
        String[] targetPhrases = {"Error accessing BKMEA BIN:", "Error accessing BGMEA BIN:"};

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                for (String phrase : targetPhrases) {
                    if (line.contains(phrase)) {
                        System.out.println(line);
                        break;
                    }
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}