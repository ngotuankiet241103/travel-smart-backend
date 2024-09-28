package com.travel.travel.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class ConvertUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public static <T> T convert(Object aMap, Class<T> t) {

        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper
                    .convertValue(aMap, t);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
