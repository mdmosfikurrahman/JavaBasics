package org.epde;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CheckBinsFromFile {

    public static void main(String[] args) {
        // Set your start and end date manually
        String startDate = "2023-08-17";
        String endDate = "2023-08-20";

        // Initialize an empty list to store valid BINs
        List<String> validBins = new ArrayList<>();

        // Path to the JSON file containing BIN data
        String jsonFilePath = "E:/Project/JavaBasics/src/main/resources/final/combine/bkmea_combined.json";

        // API URL
        String apiUrl = "http://20.205.119.208:90/api/DocumentList";

        try {
            // Read the BIN data from the JSON file
            File file = new File(jsonFilePath);
            FileReader fileReader = new FileReader(file);
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            JSONObject inputJson = new JSONObject(jsonTokener);

            // Extract the list of BINs from the JSON file
            JSONArray binData = inputJson.getJSONArray("binData");

            for (int i = 0; i < binData.length(); i++) {
                String bin = binData.getString(i);

                // Create the complete API URL with parameters
                String fullUrl = apiUrl + "?binNo=" + bin + "&startDate=" + startDate + "&endDate=" + endDate;

                // Create an HTTP connection
                URL url = new URL(fullUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Check the response code
                if (conn.getResponseCode() == 200) {
                    // Read the response
                    JSONObject jsonResponse = getJsonObject(conn);

                    // Check if the response contains a "regNo"
                    if (jsonResponse.has("regNo")) {
                        validBins.add(bin);
                    }
                }

                conn.disconnect();
            }

            // Create a JSON object with valid BINs
            JSONObject updatedJson = new JSONObject();
            updatedJson.put("binData", new JSONArray(validBins));

            // Path to save the updated JSON
            String updatedJsonFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\final\\fresh\\bkmea_final_bins.json";

            // Save the updated JSON to the specified location
            try (FileWriter fileWriter = new FileWriter(updatedJsonFilePath)) {
                updatedJson.write(fileWriter);
                System.out.println("Updated JSON saved to: " + updatedJsonFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonObject(HttpURLConnection conn) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // Parse the JSON response
        return new JSONObject(response.toString());
    }
}