package com.travelsmart.media_service.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Value("${cloud.cloudName}")
    private String cloudName;
    @Value("${cloud.apiKey}")
    private String apiKey;
    @Value("${cloud.apiSecret}")
    private String apiSecret;
    @Value("${cloud.secure}")
    private boolean secure;
    @Bean
    public Cloudinary cloud() {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret,"secure",secure));
        return cloudinary;
    }


}
