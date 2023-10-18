package org.epde;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class DecodeTrackingNumber {
    public static void main(String[] args) {
        String base64EncodedTrackingNo = "VUQzMjUzMjAyMjA0Nw==";

        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedTrackingNo);
            String originalTrackingNo = new String(decodedBytes, StandardCharsets.UTF_8);
            System.out.println("Original Tracking Number: " + originalTrackingNo);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 encoding: " + e.getMessage());
        }

        String base64DecodedTrackingNo = "BKMEA\\NGJ\\UD\\2022\\854\\22";
        try {
            byte[] encodeBytes = Base64.getEncoder().encode(base64DecodedTrackingNo.getBytes());
            String encodedTrackingNo = new String(encodeBytes, StandardCharsets.UTF_8);
            System.out.println("Decoded Tracking Number: " + encodedTrackingNo);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid Base64 decoding: " + e.getMessage());
        }
    }
}

