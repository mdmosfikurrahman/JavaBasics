package org.epde.bin;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;

public class MergeAndCountDuplicates {

    public static void main(String[] args) {
        try {
            JSONObject newJson = readJSONFile("E:\\Project\\JavaBasics\\src\\main\\resources\\final\\new\\bkmea.json");
            JSONObject oldJson = readJSONFile("E:\\Project\\JavaBasics\\src\\main\\resources\\final\\old\\bkmea_bins.json");

            DuplicateCount duplicateCount = new DuplicateCount();
            JSONObject mergedJSON = mergeWithoutDuplicates(newJson, oldJson, duplicateCount);

            String outputPath = "E:\\Project\\JavaBasics\\src\\main\\resources\\final\\combine\\bkmea_combined.json";

            writeJSONToFile(mergedJSON, outputPath);

            System.out.println("Merged JSON with duplicates removed:");
            System.out.println(mergedJSON.toString(4));

            System.out.println("Number of duplicate values: " + duplicateCount.getCount());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject readJSONFile(String filename) throws IOException {
        InputStream is = new FileInputStream(filename);
        JSONTokener tokener = new JSONTokener(is);
        return new JSONObject(tokener);
    }

    private static void writeJSONToFile(JSONObject json, String outputPath) throws IOException {
        try (FileWriter fileWriter = new FileWriter(outputPath)) {
            fileWriter.write(json.toString(4));
        }
    }

    private static JSONObject mergeWithoutDuplicates(JSONObject newJson, JSONObject oldJson, DuplicateCount duplicateCount) {
        JSONObject mergedJSON = new JSONObject();

        for (String key : newJson.keySet()) {
            if (oldJson.has(key)) {
                Object oldValue = oldJson.get(key);
                Object newValue = newJson.get(key);
                if (!oldValue.equals(newValue)) {
                    duplicateCount.increment();
                }
            }
            mergedJSON.put(key, newJson.get(key));
        }

        for (String key : oldJson.keySet()) {
            if (!newJson.has(key)) {
                mergedJSON.put(key, oldJson.get(key));
            }
        }

        return mergedJSON;
    }

    static class DuplicateCount {
        private int count = 0;

        public void increment() {
            count++;
        }

        public int getCount() {
            return count;
        }
    }
}