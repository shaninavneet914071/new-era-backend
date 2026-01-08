package com.neweraorganizations.webhook.core.security;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class WebhookSignatureVerifier {

    private static final String HMAC_SHA256 = "HmacSHA256";

    /**
     * Verifies HMAC-SHA256 signature.
     *
     * @param payload   raw request body
     * @param secret    webhook secret
     * @param signature signature from header
     * @return true if valid, false otherwise
     */
    public static boolean verify(
            String payload,
            String secret,
            String signature
    ) {
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256);
            SecretKeySpec key =
                    new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);

            mac.init(key);
            byte[] rawHmac = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));

            String calculatedSignature =
                    Base64.getEncoder().encodeToString(rawHmac);

            return constantTimeEquals(calculatedSignature, signature);

        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Prevents timing attacks.
     */
    private static boolean constantTimeEquals(String a, String b) {
        if (a.length() != b.length()) return false;

        int result = 0;
        for (int i = 0; i < a.length(); i++) {
            result |= a.charAt(i) ^ b.charAt(i);
        }
        return result == 0;
    }
}

