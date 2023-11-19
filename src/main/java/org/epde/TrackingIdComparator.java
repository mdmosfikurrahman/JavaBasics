package org.epde;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class TrackingIdComparator {
    private final Set<String> csvTrackingIds;
    private final JSONArray missingTrackingIds;

    public TrackingIdComparator(String csvFilePath) {
        this.csvTrackingIds = loadCsvTrackingIds(csvFilePath);
        this.missingTrackingIds = new JSONArray();
    }

    public void compareTrackingIdsWithJson(String jsonFilePath) {
        try {
            JSONObject json = loadJsonFromFile(jsonFilePath);
            JSONArray udArray = json.getJSONObject("data").getJSONArray("ud");
            JSONArray amArray = json.getJSONObject("data").getJSONArray("am");

            Set<String> allTrackingIds = new HashSet<>();

            // Add UD tracking IDs
            for (int i = 0; i < udArray.length(); i++) {
                allTrackingIds.add(udArray.getJSONObject(i).getString("trackingId"));
            }

            // Add AM tracking IDs
            for (int i = 0; i < amArray.length(); i++) {
                allTrackingIds.add(amArray.getJSONObject(i).getString("trackingId"));
            }

            // Find missing tracking IDs
            for (String trackingId : allTrackingIds) {
                if (!csvTrackingIds.contains(trackingId)) {
                    missingTrackingIds.put(trackingId); // Add to the JSON array
                }
            }

            System.out.println("Total number of mismatches: " + missingTrackingIds.length());

            // Save the missing tracking IDs to a JSON file
            saveMissingTrackingIdsToFile();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Set<String> loadCsvTrackingIds(String csvFilePath) {
        Set<String> trackingIds = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                trackingIds.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trackingIds;
    }

    private JSONObject loadJsonFromFile(String jsonFilePath) throws IOException {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }
        }
        return new JSONObject(jsonContent.toString());
    }

    private void saveMissingTrackingIdsToFile() {
        try (FileWriter fileWriter = new FileWriter("missing_tracking_ids.json")) {
            fileWriter.write(missingTrackingIds.toString(4)); // 4 is the number of spaces for indentation
            System.out.println("Missing tracking IDs saved to: " + "missing_tracking_ids.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String csvFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\NBR_ADMIN_BGMEA_DOCUMENT_DETAILS_REPORT.csv";
        String jsonFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\000114137-0103.json";

        TrackingIdComparator comparator = new TrackingIdComparator(csvFilePath);
        comparator.compareTrackingIdsWithJson(jsonFilePath);
    }
}