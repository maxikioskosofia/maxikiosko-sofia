/*package com.ecommerce.ecommerce.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Bean
    @ConditionalOnProperty(name = "cloudinary.enabled", havingValue = "true")
    public Cloudinary cloudinary(
        @Value("${CLOUDINARY_NAME:}") String cloudName,
        @Value("${CLOUDINARY_API_KEY:}") String apiKey,
        @Value("${CLOUDINARY_API_SECRET:}") String apiSecret
    ) {
        if (cloudName.isBlank() || apiKey.isBlank() || apiSecret.isBlank()) {
            throw new IllegalStateException("Cloudinary config is missing");
        }

        return new Cloudinary(ObjectUtils.asMap(
            "cloud_name", cloudName,
            "api_key", apiKey,
            "api_secret", apiSecret
        ));
    }

} */
