package org.epde.regNo;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BGMEARegNoExtractor {

    public static void main(String[] args) {
        List<String> allRegNos = new ArrayList<>();

        for (int page = 1; page <= 190; page++) {
            String url = "https://www.bgmea.com.bd/page/member-list?page=" + page;

            try {
                Document doc = Jsoup.connect(url).get();
                Elements regNoElements = doc.select("table.table tbody tr td:nth-child(2)");

                for (Element regNoElement : regNoElements) {
                    String regNoStr = regNoElement.text();
                    String formattedRegNo = formatMembershipID(regNoStr);
                    allRegNos.add(formattedRegNo);
                }

            } catch (IOException | NumberFormatException e) {
                e.printStackTrace();
            }
        }

        StringBuilder jsonBuilder = new StringBuilder("{\n\t\"regNo\": [\n");

        for (String regNo : allRegNos) {
            jsonBuilder.append("\t\t\"").append(regNo).append("\",\n");
        }

        if (!allRegNos.isEmpty()) {
            jsonBuilder.setLength(jsonBuilder.length() - 2);
            jsonBuilder.append("\n");
        }

        jsonBuilder.append("\t]\n}");

        try (FileWriter fileWriter = new FileWriter("regNo.json")) {
            fileWriter.write(jsonBuilder.toString());
            System.out.println("JSON written to regNo.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String formatMembershipID(String regNoStr) {
        int regNo = Integer.parseInt(regNoStr);

        return String.format("%04d", regNo);
    }
}
