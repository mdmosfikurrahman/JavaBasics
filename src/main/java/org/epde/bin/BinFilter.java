package org.epde.bin;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class BinFilter {
    public static void main(String[] args) {

        String startDate = "2022-07-01";
        String endDate = "2023-09-01";

        List<String> bgmeaBins = new ArrayList<>();
        List<String> bkmeaBins = new ArrayList<>();
        List<String> invalidBins = new ArrayList<>();

        String jsonFilePath = "E:/Project/JavaBasics/src/main/resources/new_combined_bins/new_combined_bins.json";

        String bgmeaAuthApiUrl = "http://182.160.98.85:90/api/v1/token/access";
        String bgmeaApiUrl = "http://182.160.98.85:90/api/v1/udam/infos";
        String bkmeaApiUrl = "http://20.205.119.208:90/api/DocumentList";

        String requestBody = "{\"clientId\": \"36aab894-f11b-42cd-8874-8797e0a3f062\", \"clientSecret\": \"7487466ac007d96c3c246291d1fc2d54884a8349f83a64a0c75cade982173214\"}";

        String accessToken = null;

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(bgmeaAuthApiUrl);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody));

            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                String responseJson = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse = new JSONObject(responseJson);

                accessToken = jsonResponse.getString("accessToken");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String bearerToken = accessToken;

        try {
            File file = new File(jsonFilePath);
            FileReader fileReader = new FileReader(file);
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            JSONObject inputJson = new JSONObject(jsonTokener);

            JSONArray binData = inputJson.getJSONArray("binData");

            for (int i = 0; i < binData.length(); i++) {
                String bin = binData.getString(i);

                String bgmeaUrl = bgmeaApiUrl + "?BIN=" + bin + "&StartDate=" + startDate + "&EndDate=" + endDate;

                URL bgmeaUrlForCheck = new URL(bgmeaUrl);
                HttpURLConnection bgmeaConnection = (HttpURLConnection) bgmeaUrlForCheck.openConnection();
                bgmeaConnection.setRequestMethod("GET");

                bgmeaConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);

                if (bgmeaConnection.getResponseCode() == 200) {
                    JSONObject bgmeaJsonResponse = getJsonObject(bgmeaConnection);

                    if (bgmeaJsonResponse.has("statusCode") && bgmeaJsonResponse.getInt("statusCode") == 200) {
                        if (!bgmeaJsonResponse.getJSONObject("data").isNull("membershipId")) {
                            bgmeaBins.add(bin);
                        }
                        else {
                            String bkmeaUrl = bkmeaApiUrl + "?binNo=" + bin + "&startDate=" + startDate + "&endDate=" + endDate;
                            URL bkmeaUrlForCheck = new URL(bkmeaUrl);
                            HttpURLConnection bkmeaConnection = (HttpURLConnection) bkmeaUrlForCheck.openConnection();
                            bkmeaConnection.setRequestMethod("GET");

                            if (bkmeaConnection.getResponseCode() != 500) {
                                JSONObject bkmeaJsonResponse = getJsonObject(bkmeaConnection);

                                if (bkmeaJsonResponse.has("regNo")){
                                    bkmeaBins.add(bin);
                                }

                            } else {
                                invalidBins.add(bin);
                            }
                            bkmeaConnection.disconnect();
                        }
                    }
                    bgmeaConnection.disconnect();
                }
            }

            JSONObject updatedBgmeaBinJson = new JSONObject();
            JSONObject updatedBkmeaBinJson = new JSONObject();
            JSONObject invalidBinJson = new JSONObject();

            updatedBgmeaBinJson.put("binData", new JSONArray(bgmeaBins));
            updatedBkmeaBinJson.put("binData", new JSONArray(bkmeaBins));
            invalidBinJson.put("binData", new JSONArray(invalidBins));

            String updatedBgmeaJsonFilePath = "E:/Project/JavaBasics/src/main/resources/new_combined_bins/new_bgmea_bins.json";
            String updatedBkmeaJsonFilePath = "E:/Project/JavaBasics/src/main/resources/new_combined_bins/new_bkmea_bins.json";
            String invalidBinFilePath = "E:/Project/JavaBasics/src/main/resources/new_combined_bins/invalid_bins.json";

            try (FileWriter fileWriter = new FileWriter(updatedBgmeaJsonFilePath)) {
                updatedBgmeaBinJson.write(fileWriter);
                System.out.println("Total " + bgmeaBins.size() + " BGMEA BINs saved in " + updatedBgmeaJsonFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter fileWriter = new FileWriter(updatedBkmeaJsonFilePath)) {
                updatedBkmeaBinJson.write(fileWriter);
                System.out.println("Total " + bkmeaBins.size() + " BKMEA BINs saved in " + updatedBkmeaJsonFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (FileWriter fileWriter = new FileWriter(invalidBinFilePath)) {
                invalidBinJson.write(fileWriter);
                System.out.println("Total " + invalidBins.size() + " INVALID BINs saved in " + invalidBinFilePath);
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Total BINS: " + (bkmeaBins.size() + bgmeaBins.size() + invalidBins.size()));
    }

    private static JSONObject getJsonObject(HttpURLConnection conn) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }
}