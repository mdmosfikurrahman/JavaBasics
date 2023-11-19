package org.epde;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;

public class UdFilter {

    private static final String BGMEA_AUTH_API_URL = "http://182.160.98.85:90/api/v1/token/access";
    private static final String UD_FILE_PATH = "E:/Project/JavaBasics/src/main/resources/ud.json";
    private static final String UD_DETAILS_API_URL = "http://182.160.98.85:90/api/v1/udam/details?trackingNo=";
    private static final String CLIENT_ID = "36aab894-f11b-42cd-8874-8797e0a3f062";
    private static final String CLIENT_SECRET = "7487466ac007d96c3c246291d1fc2d54884a8349f83a64a0c75cade982173214";

    public static void main(String[] args) {
        String bearerToken = getBearerToken();
        if (bearerToken == null) {
            System.err.println("Failed to obtain the bearer token. Exiting.");
            return;
        }

        try {
            JSONObject inputJson = loadJsonFromFile();

            JSONArray udTrackingNos = inputJson.getJSONArray("UD");
            int totalUds = udTrackingNos.length();

            for (int i = 0; i < totalUds; i++) {
                String udTrackingNo = udTrackingNos.getString(i);
                String udDetailsUrl = UD_DETAILS_API_URL + udTrackingNo;

                JSONObject udDetailsResponse = getUdDetails(udDetailsUrl, bearerToken);

                if (udDetailsResponse != null) {
                    String shipmentDate = udDetailsResponse.getJSONObject("data").getJSONArray("exportLc")
                            .getJSONObject(0).getString("shipmentDate");
                    String importLcOrigin = udDetailsResponse.getJSONObject("data").getJSONArray("importLc")
                            .getJSONObject(0).getString("origin");

                    if (shipmentDate.contains("2024") && "Local".equalsIgnoreCase(importLcOrigin)) {
                        System.out.println("Valid UD details for trackingNo: " + udTrackingNo);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject loadJsonFromFile() throws IOException {
        try (FileReader fileReader = new FileReader(UD_FILE_PATH)) {
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            return new JSONObject(jsonTokener);
        }
    }

    private static JSONObject getUdDetails(String udDetailsUrl, String bearerToken) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(udDetailsUrl);
            httpGet.setHeader("Authorization", "Bearer " + bearerToken);

            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                return new JSONObject(EntityUtils.toString(response.getEntity()));
            } else {
                System.err.println("Failed to obtain UD details. Status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String getBearerToken() {
        String requestBody = "{\"clientId\": \"" + CLIENT_ID + "\", \"clientSecret\": \"" + CLIENT_SECRET + "\"}";

        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(BGMEA_AUTH_API_URL);

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(new StringEntity(requestBody));

            HttpResponse response = httpClient.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                JSONObject jsonResponse = new JSONObject(EntityUtils.toString(response.getEntity()));
                return jsonResponse.getString("accessToken");
            } else {
                System.err.println("Failed to obtain the bearer token. Status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
