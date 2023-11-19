package org.epde;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class CsvToJsonConverter {

    public static void main(String[] args) {
        try {
            String csvFilePath = "E:\\Project\\JavaBasics\\src\\main\\resources\\NBR_ADMIN_BGMEA_DOCUMENT_DETAILS_REPORT.csv"; // Replace with your CSV file path
            String jsonFilePath = "output.json"; // Specify the JSON file path

            FileReader reader = new FileReader(csvFilePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            List<String> outputList = new ArrayList<>();
            for (CSVRecord record : csvParser) {
                String value = record.get(0); // Get the value from the first (and only) column
                outputList.add(value);
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(outputList);

            // Write the JSON data to the specified file
            FileWriter writer = new FileWriter(jsonFilePath);
            writer.write(json);
            writer.close();

            System.out.println("JSON data saved to " + jsonFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}