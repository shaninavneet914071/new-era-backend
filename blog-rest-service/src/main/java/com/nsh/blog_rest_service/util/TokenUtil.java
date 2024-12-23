package com.nsh.blog_rest_service.util;

import com.nsh.blog_rest_service.exception.ResourceNotFoundException;
import lombok.SneakyThrows;
import org.json.JSONObject;

import java.util.Base64;
import java.util.UUID;

public class TokenUtil {

    public static String parseHeaderToken(String headerToken) {

        String token = null;
        if (headerToken != null && headerToken.startsWith("Bearer")) {
            token = headerToken.substring(7);
        }
        else{
            throw new ResourceNotFoundException("Authentication","token",headerToken);
        }
        return token;
    }

    private static String decode(String encodedString) {
        return new String(Base64.getUrlDecoder().decode(encodedString));
    }

    @SneakyThrows
    public static JSONObject getPayloadFromToken(String token) {
        String[] parts = token.split("\\.");
        JSONObject payload = new JSONObject(decode(parts[1]));
        return payload;
    }
//
//    @SneakyThrows
//    public static String getNameFromToken(String token) {
//        String name = null;
//        JSONObject payload = getPayloadFromToken(token);
//        if (Objects.nonNull(payload)) {
//            name = payload.getString("name");
//        }
//        return name;
//    }
//
    @SneakyThrows
    public static UUID getUserIdFromToken(String token) {
        UUID userId = null;
        JSONObject payload = getPayloadFromToken(token);
        userId = UUID.fromString(payload.getString("sub"));
        return userId;
    }

}