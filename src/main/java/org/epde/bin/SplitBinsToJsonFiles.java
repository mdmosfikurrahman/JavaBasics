package org.epde.bin;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SplitBinsToJsonFiles {
    private static final String BASE_PATH = "E:/Project/JavaBasics/src/main/resources/bins/";

    public static void main(String[] args) {
        splitBins("bgmea_bins.json", "bgmea/");
        splitBins("bkmea_bins.json", "bkmea/");
    }

    private static void splitBins(String inputFileName, String outputDirectory) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT); // Enable pretty printing

            JsonNode jsonNode = objectMapper.readTree(new File(BASE_PATH + inputFileName));
            JsonNode binDataNode = jsonNode.get("binData");

            List<String> binDataList = new ArrayList<>();
            for (JsonNode bin : binDataNode) {
                binDataList.add(bin.asText());
            }

            int chunkSize = 50;
            int fileCount = 0;
            for (int i = 0; i < binDataList.size(); i += chunkSize) {
                List<String> chunk = binDataList.subList(i, Math.min(i + chunkSize, binDataList.size()));
                createJsonFile(objectMapper, outputDirectory, chunk, inputFileName, fileCount);
                fileCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createJsonFile(ObjectMapper objectMapper, String outputDirectory, List<String> binData, String inputFileName, int fileCount) {
        try {
            ObjectWriter writer = objectMapper.writer(new MyPrettyPrinter());
            ObjectNode root = objectMapper.createObjectNode();
            ArrayNode binDataNode = objectMapper.createArrayNode();

            for (String bin : binData) {
                binDataNode.add(bin);
            }

            root.set("binData", binDataNode);

            File outputFile = new File(BASE_PATH + "chunk/" + outputDirectory + inputFileName.replace(".json", "_chunk_bins_" + (fileCount + 1) + ".json"));
            writer.writeValue(outputFile, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class MyPrettyPrinter extends DefaultPrettyPrinter {
        private static final long serialVersionUID = 1L;

        public MyPrettyPrinter() {
            _arrayIndenter = DefaultIndenter.SYSTEM_LINEFEED_INSTANCE;
            _objectFieldValueSeparatorWithSpaces = ": ";
        }

        @Override
        public MyPrettyPrinter createInstance() {
            return new MyPrettyPrinter();
        }
    }
}