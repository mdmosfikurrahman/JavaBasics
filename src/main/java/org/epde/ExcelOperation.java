package org.epde;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelOperation {
    public static void main(String[] args) {
        InputStream inputStream = null;
        try {
            inputStream = ExcelOperation.class.getResourceAsStream("/input_en.xlsx");
            assert inputStream != null;
            Workbook workbook = new XSSFWorkbook(inputStream);

            Sheet sheet = workbook.getSheetAt(0);

            Map<String, String> bgmeaMap = new HashMap<>();
            Map<String, String> bkmeaMap = new HashMap<>();
            Map<String, String> bgmeaMismatchMap = new HashMap<>();
            Map<String, String> bkmeaMismatchMap = new HashMap<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String identifier = row.getCell(0).getStringCellValue();
                String vatRegistrationNumber = row.getCell(1).getStringCellValue();
                int memberOfBgmea = (int) row.getCell(2).getNumericCellValue();
                int memberOfBkmea = (int) row.getCell(3).getNumericCellValue();

                if (vatRegistrationNumber.length() == 14) {
                    if (memberOfBgmea == 1) {
                        bgmeaMap.put(identifier, vatRegistrationNumber);
                    }
                    if (memberOfBkmea == 1) {
                        bkmeaMap.put(identifier, vatRegistrationNumber);
                    }
                } else {
                    if (memberOfBgmea == 1) {
                        bgmeaMismatchMap.put(identifier, vatRegistrationNumber);
                    }
                    if (memberOfBkmea == 1) {
                        bkmeaMismatchMap.put(identifier, vatRegistrationNumber);
                    }
                }
            }

            writeCSV("bgmea.csv", bgmeaMap);
            writeCSV("bkmea.csv", bkmeaMap);
            writeCSV("bgmea_mismatch.csv", bgmeaMismatchMap);
            writeCSV("bkmea_mismatch.csv", bkmeaMismatchMap);

            System.out.println("Processing completed.");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeCSV(String fileName, Map<String, String> dataMap) throws IOException {
        FileWriter writer = new FileWriter(fileName);

        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            writer.append(entry.getKey());
            writer.append(',');
            writer.append(entry.getValue());
            writer.append('\n');
        }

        writer.flush();
        writer.close();
    }
}