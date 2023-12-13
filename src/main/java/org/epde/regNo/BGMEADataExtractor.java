package org.epde.regNo;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BGMEADataExtractor {

    public static void main(String[] args) {
        List<MembershipData> allMembershipData = new ArrayList<>();

        // Read mismatched JSON file
        List<Integer> mismatchedRegNos = readMismatchedJson();

        for (int page = 1; page <= 190; page++) {
            String url = "https://www.bgmea.com.bd/page/member-list?page=" + page;

            try {
                Document doc = Jsoup.connect(url).get();
                Elements rows = doc.select("table.table tbody tr");

                for (Element row : rows) {
                    Element nameElement = row.selectFirst("td:nth-child(1)");
                    Element regNoElement = row.selectFirst("td:nth-child(2)");

                    String name = nameElement.text();
                    int regNo = Integer.parseInt(regNoElement.text());

                    // Check if the regNo is in the mismatched list
                    if (mismatchedRegNos.contains(regNo)) {
                        MembershipData membershipData = new MembershipData(name, regNo);
                        allMembershipData.add(membershipData);
                    }
                }

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        // Generate Excel file
        generateExcelFile(allMembershipData);
    }

    private static List<Integer> readMismatchedJson() {
        List<Integer> mismatchedRegNos = new ArrayList<>();

        try (FileReader reader = new FileReader("E:\\Project\\JavaBasics\\mismatched.json")) {
            // Use Gson library to parse JSON
            JsonObject jsonObject = JsonParser.parseReader(reader).getAsJsonObject();
            JsonArray regNoArray = jsonObject.getAsJsonArray("regNo");

            for (int i = 0; i < regNoArray.size(); i++) {
                mismatchedRegNos.add(regNoArray.get(i).getAsInt());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return mismatchedRegNos;
    }

    private static void generateExcelFile(List<MembershipData> allMembershipData) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Membership Data");

            int rowNum = 0;
            for (MembershipData membershipData : allMembershipData) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(membershipData.getName());
                row.createCell(1).setCellValue(membershipData.getRegNo());
            }

            try (FileOutputStream fileOut = new FileOutputStream("membershipData.xlsx")) {
                workbook.write(fileOut);
                System.out.println("Excel written to membershipData.xlsx");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MembershipData {
        private String name;
        private int regNo;

        public MembershipData(String name, int regNo) {
            this.name = name;
            this.regNo = regNo;
        }

        public String getName() {
            return name;
        }

        public int getRegNo() {
            return regNo;
        }
    }
}