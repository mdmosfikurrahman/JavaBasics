package org.epde.bin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.*;

public class RemoveAndReplaceDuplicatesInJSONArrayFile {

    public static void main(String[] args) {
        try {
            String jsonFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\final\\combine\\bkmea_combined.json";
            String jsonArrayKey = "binData"; // Replace with the key of the JSON array to check

            String jsonString = readJSONFile(jsonFilePath);
            JSONObject jsonObject = new JSONObject(jsonString);

            JSONArray jsonArray = jsonObject.getJSONArray(jsonArrayKey);
            int originalSize = jsonArray.length();

            removeDuplicates(jsonArray);

            int newSize = jsonArray.length();

            if (originalSize != newSize) {
                // If duplicates were removed, update the JSON file
                updateJSONFile(jsonFilePath, jsonObject.toString(4));
                System.out.println("Duplicates removed, and the JSON file is updated.");
            } else {
                System.out.println("No duplicate values found in " + jsonArrayKey + ".");
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private static String readJSONFile(String filePath) throws IOException {
        StringBuilder jsonString = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
        }
        return jsonString.toString();
    }

    private static void removeDuplicates(JSONArray jsonArray) {
        Set<String> uniqueValues = new HashSet<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            String value = jsonArray.optString(i);

            if (uniqueValues.contains(value)) {
                jsonArray.remove(i);
                i--; // Adjust the index after removing an element
            } else {
                uniqueValues.add(value);
            }
        }
    }

    private static void updateJSONFile(String filePath, String updatedJsonString) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(updatedJsonString);
        }
    }
}
