package org.epde.bin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class CountBinNumbers {
    private static final String BASE_PATH = "E:/Project/JavaBasics/src/main/resources/bins/";
    public static void main(String[] args) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            int combinedBinCount = countBinNumbers(objectMapper, BASE_PATH + "all_bins.json");
            int bgmeaBinCount = countBinNumbers(objectMapper, BASE_PATH + "bgmea_bins.json");
            int bkmeaBinCount = countBinNumbers(objectMapper, BASE_PATH + "bkmea_bins.json");
            int inactiveBinCount = countBinNumbers(objectMapper, BASE_PATH + "inactive_bins.json");

            int duplicateBinCount = combinedBinCount - (bgmeaBinCount + bkmeaBinCount + inactiveBinCount);

            System.out.println("Total BIN numbers           : " + combinedBinCount);
            System.out.println("___________________________________");
            System.out.println("Total BGMEA BIN numbers     : " + bgmeaBinCount);
            System.out.println("Total BKMEA BIN numbers     : " + bkmeaBinCount);
            System.out.println("Total INACTIVE BIN numbers  : " + inactiveBinCount);
            System.out.println("___________________________________");
            System.out.println("Total DUPLICATE BIN numbers : " + duplicateBinCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int countBinNumbers(ObjectMapper objectMapper, String filePath) throws IOException {
        JsonNode dataNode = objectMapper.readTree(new File(filePath)).get("binData");
        return (dataNode != null && dataNode.isArray()) ? dataNode.size() : 0;
    }
}