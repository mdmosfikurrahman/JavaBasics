package org.epde.regNo;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class JsonCompareWithExistingAndNewSimilar {
    public static void main(String[] args) {
        try {
            // Specify the paths to your JSON files
            String filePath1 = "E:\\Project\\JavaBasics\\src\\main\\resources\\present.json";
            String filePath2 = "E:\\Project\\JavaBasics\\src\\main\\resources\\new\\members\\inactive.json";

            // Create ObjectMapper instance
            ObjectMapper objectMapper = new ObjectMapper();

            // Read JSON files into JsonNode objects
            JsonNode node1 = objectMapper.readTree(new File(filePath1));
            JsonNode node2 = objectMapper.readTree(new File(filePath2));

            // Extract "regNo" arrays from JsonNode objects
            JsonNode regNo1 = node1.get("regNo");
            JsonNode regNo2 = node2.get("regNo");

            // Find common values
            Set<Integer> set1 = convertJsonNodeToSet(regNo1);
            Set<Integer> set2 = convertJsonNodeToSet(regNo2);

            set1.retainAll(set2);

            // Create output JSON object
            ArrayNode outputArray = new ArrayNode(JsonNodeFactory.instance);
            for (Integer value : set1) {
                outputArray.add(value);
            }

            // Create output JSON object
            ObjectNode outputJson = new ObjectNode(JsonNodeFactory.instance);
            outputJson.set("regNo", outputArray);

            System.out.println(outputJson);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Set<Integer> convertJsonNodeToSet(JsonNode array) {
        Set<Integer> set = new HashSet<>();
        Iterator<JsonNode> iterator = array.elements();
        while (iterator.hasNext()) {
            set.add(iterator.next().intValue());
        }
        return set;
    }
}
