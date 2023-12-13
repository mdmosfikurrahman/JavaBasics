package org.epde.regNo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonComparator {
    public static void main(String[] args) {
        try {
            JsonNode presentJson = readJsonFile("E:\\Project\\JavaBasics\\src\\main\\resources\\present.json");
            JsonNode newJson = readJsonFile("E:\\Project\\JavaBasics\\src\\main\\resources\\new\\members\\active.json");

            List<Integer> presentList = extractList(presentJson);
            List<Integer> newList = extractList(newJson);

            System.out.println("Present List: " + presentList);
            System.out.println("New List: " + newList);

            List<Integer> matchedValues = findMatchedValues(presentList, newList);
            List<Integer> mismatchedValues = findMismatchedValues(presentList, newList);

            System.out.println("Matched Values: " + matchedValues);
            System.out.println("Mismatched Values: " + mismatchedValues);

            writeJsonFiles(matchedValues, mismatchedValues);

            System.out.println("Matched and mismatched values saved to matched.json and mismatched.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonNode readJsonFile(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readTree(new File(fileName));
    }

    private static void writeJsonFiles(List<Integer> matchedValues, List<Integer> mismatchedValues) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        // Write matched values to matched.json
        ObjectNode matchedNode = mapper.createObjectNode();
        matchedNode.set("regNo", mapper.valueToTree(matchedValues));
        mapper.writeValue(new File("matched.json"), matchedNode);

        // Write mismatched values to mismatched.json
        ObjectNode mismatchedNode = mapper.createObjectNode();
        mismatchedNode.set("regNo", mapper.valueToTree(mismatchedValues));
        mapper.writeValue(new File("mismatched.json"), mismatchedNode);
    }

    private static List<Integer> findMatchedValues(List<Integer> presentList, List<Integer> newList) {
        List<Integer> matchedValues = new ArrayList<>(newList);
        matchedValues.retainAll(presentList);
        return matchedValues;
    }

    private static List<Integer> findMismatchedValues(List<Integer> presentList, List<Integer> newList) {
        List<Integer> mismatchedValues = new ArrayList<>(newList);
        mismatchedValues.removeAll(presentList);
        return mismatchedValues;
    }

    private static List<Integer> extractList(JsonNode jsonNode) {
        List<Integer> list = new ArrayList<>();
        if (jsonNode.has("regNo")) {
            jsonNode.get("regNo").forEach(value -> list.add(value.asInt()));
        }
        return list;
    }
}
