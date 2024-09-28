//package com.identity_service.identity.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.apache.kafka.common.header.Headers;
//import org.springframework.kafka.support.serializer.JsonSerializer;
//
//public class CustomSerializer<T> extends JsonSerializer<T> {
//
//    public CustomSerializer() {
//        super();
//    }
//
//    public CustomSerializer(ObjectMapper objectMapper) {
//        super(objectMapper);
//    }
//
//    @Override
//    public byte[] serialize(String topic, Headers headers, T data) {
//        // Chỉ lưu tên class (không bao gồm package)
//        headers.add("className", data.getClass().getSimpleName().getBytes());
//        return super.serialize(topic, headers, data);
//    }
//}
