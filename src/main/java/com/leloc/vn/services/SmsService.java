package com.leloc.vn.services;
import com.leloc.vn.controllers.api.SmsResponse;
import org.cloudinary.json.JSONException;
import org.cloudinary.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SmsService {

    private final RestTemplate restTemplate;

    public SmsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public SmsResponse getContent(String token) {
        String url = "http://sms222.us?token=" + token;
        String response = restTemplate.getForObject(url, String.class);

        // Log raw response
        System.out.println("Raw Response: " + response);

        // If the response starts with '{' it's likely JSON
        if (response != null && response.startsWith("{")) {
            try {
                JSONObject jsonResponse = new JSONObject(response);
                System.out.println("Parsed JSON Response: " + jsonResponse);  // Log the full JSON

                if ("fail".equalsIgnoreCase(jsonResponse.getString("status"))) {
                    return new SmsResponse("fail", "Chưa có OTP");
                } else {
                    // Extract the message from the response
                    String message = jsonResponse.getJSONObject("data").getString("message");
                    System.out.println("Extracted Message: " + message);  // Log the message

                    String verificationCode = extractVerificationCode(message);
                    System.out.println("Extracted OTP: " + verificationCode);  // Log the extracted OTP

                    return new SmsResponse("success", verificationCode);
                }
            } catch (JSONException e) {
                return new SmsResponse("error", "Error parsing JSON response");
            }
        } else {
            // If the response is plain text, extract the OTP directly from it
            String verificationCode = extractVerificationCode(response);
            System.out.println("Extracted OTP from plain text: " + verificationCode);  // Log OTP from plain text
            return new SmsResponse("success", verificationCode);
        }
    }

    private String extractVerificationCode(String message) {
        // Use a regular expression to extract the 6 digits after "G-"
        String regex = "G-(\\d{6})";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(message);

        if (matcher.find()) {
            return matcher.group(1);  // Return the 6 digits after "G-"
        }

        return "Invalid OTP format";  // In case the format is not as expected
    }
}
