package org.epde;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class ApiSpeedTest {

    public static void main(String[] args) {
        // Define the URLs of the two APIs you want to test
        String api1Url = "http://localhost:8890/api/v1/udam/details?trackingNo=UD51512022002-005";
        String api2Url = "http://localhost:8890/api/v1/query/udam/details?trackingNo=UD51512022002-005";

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        List<Long> api1ResponseTimes = new ArrayList<>();
        List<Long> api2ResponseTimes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            // Time the response time for API 1
            long startTime1 = System.currentTimeMillis();
            ResponseEntity<String> response1 = restTemplate.getForEntity(api1Url, String.class);
            long endTime1 = System.currentTimeMillis();
            long api1ResponseTime = endTime1 - startTime1;
            api1ResponseTimes.add(api1ResponseTime);

            // Time the response time for API 2
            long startTime2 = System.currentTimeMillis();
            ResponseEntity<String> response2 = restTemplate.getForEntity(api2Url, String.class);
            long endTime2 = System.currentTimeMillis();
            long api2ResponseTime = endTime2 - startTime2;
            api2ResponseTimes.add(api2ResponseTime);

            System.out.println("Iteration " + (i + 1) + ":");
            System.out.println("API 1 Response Time: " + api1ResponseTime + " ms");
            System.out.println("API 2 Response Time: " + api2ResponseTime + " ms");
        }

        // Determine which API is the best
        long bestApi1ResponseTime = api1ResponseTimes.stream().min(Long::compare).orElse(0L);
        long bestApi2ResponseTime = api2ResponseTimes.stream().min(Long::compare).orElse(0L);

        if (bestApi1ResponseTime < bestApi2ResponseTime) {
            System.out.println("API 1 is the best. Best response time: " + bestApi1ResponseTime + " ms");
        } else if (bestApi2ResponseTime < bestApi1ResponseTime) {
            System.out.println("API 2 is the best. Best response time: " + bestApi2ResponseTime + " ms");
        } else {
            System.out.println("Both APIs have the same best response time.");
        }
    }
}