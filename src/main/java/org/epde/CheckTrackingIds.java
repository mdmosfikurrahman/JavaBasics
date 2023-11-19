package org.epde;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckTrackingIds {
    public static void main(String[] args) {
        String bearerToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9uYW1lIjoiMzZhYWI4OTQtZjExYi00MmNkLTg4NzQtODc5N2UwYTNmMDYyIiwiaHR0cDovL3NjaGVtYXMueG1sc29hcC5vcmcvd3MvMjAwNS8wNS9pZGVudGl0eS9jbGFpbXMvbmFtZWlkZW50aWZpZXIiOiI5MjExNzk3ZS1jYTI4LTRhMjAtYmIxMS1lZTk5ODBmNTYwYjQiLCJodHRwOi8vc2NoZW1hcy54bWxzb2FwLm9yZy93cy8yMDA1LzA1L2lkZW50aXR5L2NsYWltcy9naXZlbm5hbWUiOiJDQk1TLVVBVCIsImV4cCI6MTY5OTE2MTA3MywiaXNzIjoiaHR0cDovL2JnbXQuYnJhaW5zdGF0aW9uMjMuY29tIiwiYXVkIjoiTkJSIn0.V1PQDP0vUdiHeTolJZataY-THCRTh7030k4jilp46HA";

        try {
//            String jsonFileName = "E:\\Project\\JavaBasics\\missing_tracking_ids.json";
            String jsonFileName = "E:\\Project\\JavaBasics\\output.json";
            StringBuilder jsonContent = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(jsonFileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonContent.append(line);
                }
            }

            JSONArray trackingIds = new JSONArray(jsonContent.toString());

            for (int i = 0; i < trackingIds.length(); i++) {
                String trackingId = trackingIds.getString(i);

                String apiUrl = "http://182.160.98.85:90/api/v1/udam/details?trackingNo=" + trackingId;

                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                    connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
                    connection.setRequestMethod("GET");

                    int responseCode = connection.getResponseCode();

                    if (responseCode == 200) {
                        JSONObject amDyesChemicalReports = getJsonObject(connection);

                        JSONArray amDyesChemicalTypeBReports = amDyesChemicalReports.getJSONArray("amDyesChemicalTypeBReports");
                        JSONArray amDyesChemicalTypeC1Reports = amDyesChemicalReports.getJSONArray("amDyesChemicalTypeC1Reports");
                        JSONArray amDyesChemicalTypeC2Reports = amDyesChemicalReports.getJSONArray("amDyesChemicalTypeC2Reports");

                        if (!amDyesChemicalTypeBReports.isEmpty() ||
                                !amDyesChemicalTypeC1Reports.isEmpty() ||
                                !amDyesChemicalTypeC2Reports.isEmpty()) {
                            System.out.println("Tracking ID " + trackingId + " has non-empty fields.");
                        }
                    } else {
                        System.out.println("Failed to fetch data for Tracking ID " + trackingId + ". HTTP Response Code: " + responseCode);
                    }

                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonObject(HttpURLConnection connection) throws IOException {
        BufferedReader apiResponseReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = apiResponseReader.readLine()) != null) {
            response.append(line);
        }
        apiResponseReader.close();

        JSONObject jsonResponse = new JSONObject(response.toString());

        JSONObject data = jsonResponse.getJSONObject("data");
        return data.getJSONObject("amDyesChemicalReports");
    }
}