package org.epde;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CheckBinsWithAuth {

    public static void main(String[] args) {
        // Set your start and end date manually
        String startDate = "2022-07-01";
        String endDate = "2023-09-01";

        // Initialize an empty list to store valid BINs
        List<String> validBins = new ArrayList<>();

        // Path to the JSON file containing BIN data
        String jsonFilePath = "E:/Project/JavaBasics/src/main/resources/final/combine/bgmea_combined.json";

        // New API URL
        String apiUrl = "http://182.160.98.85:90/api/v1/udam/infos";

        // Bearer token for authentication
        String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiMzZhYWI4OTQtZjExYi00MmNkLTg4NzQtODc5N2UwYTNmMDYyIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiI5MjExNzk3ZS1jYTI4LTRhMjAtYmIxMS1lZTk5ODBmNTYwYjQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOiJDQk1TLVVBVCIsImV4cCI6MTY5MzkwMDg0MSwiaXNzIjoiaHR0cDovL2JnbXQuYnJhaW5zdGF0aW9uMjMuY29tIiwiYXVkIjoiTkJSIn0.5k6MxVZ23ylx8rLbNgUGe0XrJYRCHFXnNBpXkoGh_-I";

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
                String fullUrl = apiUrl + "?BIN=" + bin + "&StartDate=" + startDate + "&EndDate=" + endDate;

                // Create an HTTP connection
                URL url = new URL(fullUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                // Set the Bearer token for authentication
                conn.setRequestProperty("Authorization", "Bearer " + bearerToken);

                // Check the response code
                if (conn.getResponseCode() == 200) {
                    // Read the response
                    JSONObject jsonResponse = getJsonObject(conn);

                    // Check for server errors
                    if (jsonResponse.has("statusCode") && jsonResponse.getInt("statusCode") == 200) {
                        // Check if "membershipId" is not null
                        if (!jsonResponse.getJSONObject("data").isNull("membershipId")) {
                            validBins.add(bin);
                        }
                    }
                } else {
                    // Handle errors here if needed
                    System.out.println("Error for BIN " + bin + ": " + conn.getResponseCode());
                }

                conn.disconnect();
            }

            // Create a JSON object with valid BINs
            JSONObject updatedJson = new JSONObject();
            updatedJson.put("binData", new JSONArray(validBins));

            // Path to save the updated JSON
            String updatedJsonFilePath = "E:/Project/JavaBasics/src/main/resources/final/fresh/bgmea_final_bins.json";

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