package org.epde.bin;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ThirteenDigitBinValidator {
    private static JSONObject loadJsonFromFile() throws IOException {
        try (FileReader fileReader = new FileReader("E:\\Project\\JavaBasics\\src\\main\\resources\\all\\bgmea_all_bins.json")) {
            JSONTokener jsonTokener = new JSONTokener(fileReader);
            return new JSONObject(jsonTokener);
        }
    }

    private static void saveJsonToFile(String filePath, JSONObject jsonObject) throws IOException {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toString(4)); // Use 4 for indentation
        }
    }

    public static void main(String[] args) throws IOException {
        JSONObject inputJson = loadJsonFromFile();
        JSONArray binDataArray = inputJson.getJSONArray("binData");

        JSONArray validBinDataArray = new JSONArray();
        JSONArray invalidBinDataArray = new JSONArray();

        for (int i = 0; i < binDataArray.length(); i++) {
            String binNumber = binDataArray.getString(i);
            if (isValidBinNumber(binNumber)) {
                validBinDataArray.put(binNumber);
            } else {
                invalidBinDataArray.put(binNumber);
            }
        }

        JSONObject validJson = new JSONObject();
        validJson.put("binData", validBinDataArray);

        JSONObject invalidJson = new JSONObject();
        invalidJson.put("binData", invalidBinDataArray);

        saveJsonToFile("E:\\Project\\JavaBasics\\src\\main\\resources\\all\\valid_bins.json", validJson);
        saveJsonToFile("E:\\Project\\JavaBasics\\src\\main\\resources\\all\\invalid_bins.json", invalidJson);

        System.out.println("Provided BGMEA BINs: " + (validBinDataArray.length() + invalidBinDataArray.length()));
        System.out.println("13 Digit's BGMEA BINs: " + validBinDataArray.length());
        System.out.println("Not 13 Digit's BGMEA BINs: " + invalidBinDataArray.length());
    }

    private static boolean isValidBinNumber(String binNumber) {
        if (binNumber.length() != 14) {
            return false;
        }

        return binNumber.charAt(0) == '0' && binNumber.charAt(10) == '0';
    }
}
