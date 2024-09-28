//package com.travelsmart.profile_service.exception;
//
//import com.travelsmart.event.dto.ProfileRequest;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CustomProfileDeserializer extends JsonDeserializer<ProfileRequest> {
//    @Override
//    public ProfileRequest deserialize(String topic, byte[] data) {
//        try {
//            return super.deserialize(topic, data);
//        } catch (Exception e) {
//            // Log or handle deserialization error
//            System.err.println("Failed to deserialize message: " + e.getMessage());
//            return null; // Or handle fallback logic
//        }
//    }
//}
