package com.nsh.customerservice.util;

import lombok.SneakyThrows;
import org.springframework.boot.configurationprocessor.json.JSONObject;

import java.util.Base64;
import java.util.UUID;
public class TokenData {
    public static String parseHeaderToken(String headerToken) {
        String token = null;
        if (headerToken != null && headerToken.startsWith("Bearer")) {
            token = headerToken.substring(7);
        }
        return token;
    }
    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }
    @SneakyThrows
    public static JSONObject getPayloadFromToken(String token) {
        String[] parts = token.split("\\.");
        return new JSONObject(decode(parts[1]));
    }
    @SneakyThrows
    public static String getNameFromToken(String token) {
        String name = null;
        JSONObject payload = getPayloadFromToken(token);
        name = payload.getString("name");
        return name;
    }
    @SneakyThrows
    public static UUID getUserIdFromToken(String token) {
        UUID userId = null;
        JSONObject payload = getPayloadFromToken(token);
        userId = UUID.fromString(payload.getString("sub"));
        return userId;
    }

    @SneakyThrows
    public static String getEmailFromToken(String token) {

        JSONObject payload = getPayloadFromToken(token);
        return payload.getString("preferred_username");

    }
}
