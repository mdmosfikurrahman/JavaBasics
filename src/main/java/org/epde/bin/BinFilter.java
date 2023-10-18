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
    private static final String BGMEA_AUTH_API_URL = "http://182.160.98.85:90/api/v1/token/access";
    private static final String BGMEA_API_URL = "http://182.160.98.85:90/api/v1/udam/infos";
    private static final String BKMEA_API_URL = "http://20.205.119.208:90/api/DocumentList";
    private static final String CLIENT_ID = "36aab894-f11b-42cd-8874-8797e0a3f062";
    private static final String CLIENT_SECRET = "7487466ac007d96c3c246291d1fc2d54884a8349f83a64a0c75cade982173214";
    private static final String START_DATE = "2022-07-01";
    private static final String END_DATE = "2023-09-01";

    public static void main(String[] args) {
        List<String> bgmeaBins = new ArrayList<>();
        List<String> bkmeaBins = new ArrayList<>();
        List<String> invalidBins = new ArrayList<>();

        // Attempt to obtain the bearer token
        String bearerToken = getBearerToken();
        if (bearerToken == null) {
            System.err.println("Failed to obtain the bearer token. Exiting.");
            return;
        }

        try {
            // Load JSON data from the file
            JSONObject inputJson = loadJsonFromFile();

            JSONArray binData = inputJson.getJSONArray("binData");

            for (int i = 0; i < binData.length(); i++) {
                String bin = binData.getString(i);
                String bgmeaUrl = createBgmeaApiUrl(bin);
                String bkmeaUrl = createBkmeaApiUrl(bin);

                if (checkBinAndAdd(bgmeaUrl, bearerToken, bgmeaBins, bin) || checkBinAndAdd(bkmeaUrl, null, bkmeaBins, bin)) {
                    continue;
                }

                invalidBins.add(bin);
            }

            // Save results to JSON files
            saveToJsonFile(bgmeaBins, "E:/Project/JavaBasics/src/main/resources/new_combined_bins/new_bgmea_bins.json");
            saveToJsonFile(bkmeaBins, "E:/Project/JavaBasics/src/main/resources/new_combined_bins/new_bkmea_bins.json");
            saveToJsonFile(invalidBins, "E:/Project/JavaBasics/src/main/resources/new_combined_bins/invalid_bins.json");

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Total BINS: " + (bkmeaBins.size() + bgmeaBins.size() + invalidBins.size()));
    }

    private static String createBgmeaApiUrl(String bin) {
        return BGMEA_API_URL + "?BIN=" + bin + "&StartDate=" + START_DATE + "&EndDate=" + END_DATE;
    }

    private static String createBkmeaApiUrl(String bin) {
        return BKMEA_API_URL + "?binNo=" + bin + "&startDate=" + START_DATE + "&endDate=" + END_DATE;
    }

    private static boolean checkBinAndAdd(String apiUrl, String token, List<String> binList, String bin) {
        boolean isBin = (token != null
                ? checkBgmeaBin(apiUrl, token)
                : checkBkmeaBin(apiUrl));
        if (isBin) {
            binList.add(bin);
        }
        return isBin;
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

    private static JSONObject loadJsonFromFile() throws IOException {
        try (FileReader fileReader = new FileReader("E:/Project/JavaBasics/src/main/resources/new_combined_bins/new_combined_bins.json")) {
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            return new JSONObject(jsonTokener);
        }
    }

    private static boolean checkBgmeaBin(String bgmeaUrl, String bearerToken) {
        try {
            URL bgmeaUrlForCheck = new URL(bgmeaUrl);
            HttpURLConnection bgmeaConnection = (HttpURLConnection) bgmeaUrlForCheck.openConnection();
            bgmeaConnection.setRequestMethod("GET");

            bgmeaConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);

            if (bgmeaConnection.getResponseCode() == 200) {
                JSONObject bgmeaJsonResponse = getJsonObject(bgmeaConnection);

                if (bgmeaJsonResponse.has("statusCode") && bgmeaJsonResponse.getInt("statusCode") == 200) {
                    if (!bgmeaJsonResponse.getJSONObject("data").isNull("membershipId")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkBkmeaBin(String bkmeaUrl) {
        try {
            URL bkmeaUrlForCheck = new URL(bkmeaUrl);
            HttpURLConnection bkmeaConnection = (HttpURLConnection) bkmeaUrlForCheck.openConnection();
            bkmeaConnection.setRequestMethod("GET");

            if (bkmeaConnection.getResponseCode() != 500) {
                JSONObject bkmeaJsonResponse = getJsonObject(bkmeaConnection);

                if (bkmeaJsonResponse.has("regNo")) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static void saveToJsonFile(List<String> bins, String filePath) {
        JSONObject binJson = new JSONObject();
        binJson.put("binData", new JSONArray(bins));

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            binJson.write(fileWriter);
            System.out.println("Total " + bins.size() + " BINs saved in " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JSONObject getJsonObject(HttpURLConnection conn) throws IOException {
        try (InputStream inputStream = conn.getInputStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            return new JSONObject(response.toString());
        }
    }
}