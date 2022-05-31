package com.backend.challenge.rockets.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.function.Supplier;

public class ConversionUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T jsonToObject(String string, Class<T> objectClass, Supplier<String> exceptionMessage) {
        try {
            return objectMapper.readValue(string, objectClass);
        } catch (IOException e) {
            throw new RuntimeException(exceptionMessage.get(), e);
        }
    }
}


