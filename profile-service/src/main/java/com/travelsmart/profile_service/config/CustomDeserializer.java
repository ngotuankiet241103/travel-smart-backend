//package com.travelsmart.profile_service.config;
//
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.header.Headers;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//public class CustomDeserializer<T> extends JsonDeserializer<T> {
//
//    private final String basePackage = "com.travelsmart.event.dto";
//
////    public CustomDeserializer(String basePackage) {
////        this.basePackage = basePackage;
////    }
//
//    @Override
//    public T deserialize(String topic, Headers headers, byte[] data) {
//        String className = new String(headers.lastHeader("className").value());
//        try {
//            // Tìm class trong package cụ thể
//            Class<?> clazz = Class.forName(basePackage + "." + className);
//            ObjectMapper objectMapper = new ObjectMapper();
//            return (T) objectMapper.readValue(data, clazz);
//        } catch (Exception e) {
//            throw new RuntimeException("Class not found or deserialization failed", e);
//        }
//    }
//}
