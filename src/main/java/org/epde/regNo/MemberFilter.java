package org.epde.regNo;

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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MemberFilter {
    private static final String BGMEA_AUTH_API_URL = "http://36.255.69.82:81/api/v1/token/access";
    private static final String CLIENT_ID = "36aab894-f11b-42cd-8874-8797e0a3f062";
    private static final String CLIENT_SECRET = "7487466ac007d96c3c246291d1fc2d54884a8349f83a64a0c75cade982173214";
    private static final String BGMEA_API_URL = "http://36.255.69.82:81/api/v1/member/infos";
    private static final String COMBINED_REG_NO_FILE_PATH = "G:\\Personal Project\\JavaBasics\\src\\main\\resources\\new\\members\\regNo.json";
    private static final String ACTIVE_REG_NO_FILE_PATH = "G:\\Personal Project\\JavaBasics\\src\\main\\resources\\new\\members\\active.json";
    private static final String INACTIVE_REG_NO_FILE_PATH = "G:\\Personal Project\\JavaBasics\\src\\main\\resources\\new\\members\\inactive.json";

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
        try (FileReader fileReader = new FileReader(COMBINED_REG_NO_FILE_PATH)) {
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            return new JSONObject(jsonTokener);
        }
    }

    private static String createBgmeaApiUrl(String memberShipNo) {
        return BGMEA_API_URL + "?membershipId=" + memberShipNo ;
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

    private static boolean checkBgmeaMember(String bgmeaUrl, String bearerToken) {
        try {
            URL bgmeaUrlForCheck = new URL(bgmeaUrl);
            HttpURLConnection bgmeaConnection = (HttpURLConnection) bgmeaUrlForCheck.openConnection();
            bgmeaConnection.setRequestMethod("GET");

            bgmeaConnection.setRequestProperty("Authorization", "Bearer " + bearerToken);

            if (bgmeaConnection.getResponseCode() == 200) {
                JSONObject bgmeaJsonResponse = getJsonObject(bgmeaConnection);

                if (bgmeaJsonResponse.has("statusCode") && bgmeaJsonResponse.getInt("statusCode") == 200) {
                    if (!bgmeaJsonResponse.getJSONObject("data").isNull("memberShipNo")) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static boolean checkBinAndAdd(String apiUrl, String token, List<Integer> regNo, String bin) {
        boolean isMember = checkBgmeaMember(apiUrl, token);

        if (isMember) {
            regNo.add(Integer.valueOf(bin));
        }
        return isMember;
    }

    private static String getProgressBar(int completed, int total) {
        int progressBarWidth = 40;
        double progress = (double) completed / total;
        int numCharsToDisplay = (int) (progress * progressBarWidth);

        StringBuilder progressBar = new StringBuilder();
        for (int i = 0; i < progressBarWidth; i++) {
            if (i < numCharsToDisplay) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }
        return progressBar.toString();
    }


    public static void main(String[] args) {
        List<Integer> activeMembers = new ArrayList<>();
        List<Integer> inactiveMembers = new ArrayList<>();

        String bearerToken = getBearerToken();
        if (bearerToken == null) {
            System.err.println("Failed to obtain the bearer token. Exiting.");
            return;
        }

        try {
            JSONObject inputJson = loadJsonFromFile();

            JSONArray regNo = inputJson.getJSONArray("regNo");
            int totalRegNos = regNo.length();

            for (int i = 0; i < totalRegNos; i++) {
                String memberShipNo = regNo.get(i).toString();
                String bgmeaUrl = createBgmeaApiUrl(memberShipNo);

                boolean isActive = checkBinAndAdd(bgmeaUrl, bearerToken, activeMembers, memberShipNo);

                if (isActive) {
                    continue;
                }

                inactiveMembers.add(Integer.valueOf(memberShipNo));

                System.out.print("\r[" + getProgressBar(i + 1, totalRegNos) + "] " + String.format("%.1f%%", (double) (i + 1) / totalRegNos * 100) + " - Processed REG NO: " + memberShipNo);
                Thread.sleep(100);
            }

            System.out.println();
            System.out.println("Active Members: " + (long) activeMembers.size());
            System.out.println();
            System.out.println("Inactive Members: " + (long) inactiveMembers.size());


            System.out.println();
            saveAndPrint(activeMembers, ACTIVE_REG_NO_FILE_PATH);
            saveAndPrint(inactiveMembers, INACTIVE_REG_NO_FILE_PATH);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void saveAndPrint(List<Integer> regNo, String destinationFilePath) {
        saveToJsonFile(regNo, destinationFilePath);
    }

    private static void saveToJsonFile(List<Integer> regNo, String filePath) {
        Set<Integer> uniqueRegNo = new HashSet<>(regNo);
        List<Integer> uniqueRegNoList = new ArrayList<>(uniqueRegNo);

        JSONObject binJson = new JSONObject();
        binJson.put("regNo", new JSONArray(uniqueRegNoList));

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            binJson.write(fileWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
