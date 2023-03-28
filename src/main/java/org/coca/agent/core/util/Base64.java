package org.coca.agent.core.util;

import java.nio.charset.StandardCharsets;

public class Base64 {
    private static final java.util.Base64.Decoder DECODER = java.util.Base64.getDecoder();
    private static final java.util.Base64.Encoder ENCODER = java.util.Base64.getEncoder();

    private Base64() {
    }

    public static String decode2UTFString(String in) {
        return new String(DECODER.decode(in), StandardCharsets.UTF_8);
    }

    public static String encode(String text) {
        return ENCODER.encodeToString(text.getBytes(StandardCharsets.UTF_8));
    }
}
