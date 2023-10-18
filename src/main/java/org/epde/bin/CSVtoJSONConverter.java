package org.epde.bin;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVtoJSONConverter {

    public static void main(String[] args) {
        String csvFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\all\\bgmea_all_bins.csv"; // Replace with your CSV file path
        String jsonFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\all\\bgmea_all_bins.json"; // Replace with the desired JSON file path

        try {
            List<String> binDataList = readCSV(csvFilePath);
            if (!binDataList.isEmpty()) {
                generateJSON(binDataList, jsonFilePath);
                System.out.println("JSON file generated successfully.");
            } else {
                System.out.println("No data found in the CSV file.");
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private static List<String> readCSV(String csvFilePath) throws IOException, CsvValidationException {
        List<String> binDataList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {
                // Assuming that the bin data is in the first column of the CSV
                if (nextRecord.length > 0) {
                    binDataList.add(nextRecord[0]);
                }
            }
        }
        return binDataList;
    }

    private static void generateJSON(List<String> binDataList, String jsonFilePath) throws IOException {
        Gson gson = new Gson();
        BinDataContainer container = new BinDataContainer(binDataList);

        try (FileWriter writer = new FileWriter(jsonFilePath)) {
            gson.toJson(container, writer);
        }
    }

    static class BinDataContainer {
        private final List<String> binData;

        public BinDataContainer(List<String> binData) {
            this.binData = binData;
        }

        public List<String> getBinData() {
            return binData;
        }
    }
}
