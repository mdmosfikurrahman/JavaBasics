package org.epde.udam;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BkmeaUdFilter {

    private static final String UD_FILE_PATH = "E:/Project/JavaBasics/src/main/resources/udam/bkmea_ud.json";
    private static final String BKMEA_UD_DETAILS_API_URL = "http://20.205.119.208:90/api/DocumentDetails?docNo=";

    public static void main(String[] args) {

        try {
            JSONObject inputJson = loadJsonFromFile();

            JSONArray udTrackingNos = inputJson.getJSONArray("UD");
            int totalUds = udTrackingNos.length();

            for (int i = 0; i < totalUds; i++) {
                String udTrackingNo = udTrackingNos.getString(i);
                String encodedUdTrackingNo = java.net.URLEncoder.encode(udTrackingNo, StandardCharsets.UTF_8);
                String udDetailsUrl = BKMEA_UD_DETAILS_API_URL + encodedUdTrackingNo;


                JSONObject udDetailsResponse = getUdDetails(udDetailsUrl);

                if (udDetailsResponse != null) {
                    String shipmentDate = udDetailsResponse.getJSONArray("exportLc")
                            .getJSONObject(0)
                            .getString("shipmentDate");

                    System.out.println(shipmentDate);
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

    private static JSONObject getUdDetails(String udDetailsUrl) {
        try {
            HttpClient httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(udDetailsUrl);

            HttpResponse response = httpClient.execute(httpGet);

            if (response.getStatusLine().getStatusCode() == 200) {
                return new JSONObject(EntityUtils.toString(response.getEntity()));
            } else {
                System.err.println("Failed to obtain details. Status code: " + response.getStatusLine().getStatusCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
